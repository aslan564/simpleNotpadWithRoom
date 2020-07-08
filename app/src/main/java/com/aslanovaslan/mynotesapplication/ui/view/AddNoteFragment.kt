package com.aslanovaslan.mynotesapplication.ui.view

import android.content.Context
import android.os.AsyncTask
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import com.aslanovaslan.mynotesapplication.R
import com.aslanovaslan.mynotesapplication.db.Note
import com.aslanovaslan.mynotesapplication.db.NoteDatabase
import com.aslanovaslan.mynotesapplication.ui.internal.BaseFragment
import com.aslanovaslan.mynotesapplication.ui.internal.EventbusSender
import com.aslanovaslan.mynotesapplication.ui.internal.toast
import kotlinx.android.synthetic.main.fragment_add_note.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe

class AddNoteFragment : BaseFragment() {

   private var dataNote: Note? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {

        }
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_note, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        arguments?.let {
             dataNote=AddNoteFragmentArgs.fromBundle(it).setNote
            if (dataNote!=null) {
                etNoteHeader.setText(dataNote!!.title)
                etNoteBody.setText(dataNote!!.note)
            }
        }


        saveNotesBackToList.setOnClickListener {

            updateActionBarTitle()
            val notHeader = etNoteHeader.text.toString().trim()
            val notBody = etNoteBody.text.toString().trim()
            if (checkNoteInputValue(notHeader, notBody)) {
                return@setOnClickListener
            }

            val mNot = Note(notHeader, notBody)
            if (dataNote == null) {
                saveNoteWithCorotToDatabase(mNot)
             //   dataNote = null
            } else {
                mNot.id = dataNote!!.id
                updateNoteWithCorotToDatabase(mNot)
            }

        }
    }

    private fun updateActionBarTitle() {
        (activity as? AppCompatActivity)?.supportActionBar?.title = "Create New Not"
    }

/*
    @Subscribe(sticky = true)
    internal fun getNoteData(note: EventbusSender.SendUpdatedNote) {
        this.note = note.note
    }
*/

    private fun updateNoteWithCorotToDatabase(not: Note) {
        launch(Dispatchers.IO) {
            context?.let {
                NoteDatabase(requireContext()).getNoteDao().updateNot(not)
                withContext(Dispatchers.Main) {
                    val action =
                        AddNoteFragmentDirections.actionBackNoteList()
                    Navigation.findNavController(requireView()).navigate(action)
                    requireActivity().toast("Note Updated")
                }
            }
        }
    }

    private fun saveNoteWithCorotToDatabase(not: Note) {
        launch(Dispatchers.IO) {
            context?.let {
                NoteDatabase(requireContext()).getNoteDao().insertNote(not)
                withContext(Dispatchers.Main) {
                    val action =
                        AddNoteFragmentDirections.actionBackNoteList()
                    Navigation.findNavController(requireView()).navigate(action)
                    requireActivity().toast("Note Saved")
                }
            }
        }
    }


    private fun checkNoteInputValue(notHeader: String, notBody: String): Boolean {
        if (notBody.isEmpty()) {
            etNoteBody.error = "Not description is required"
            etNoteBody.requestFocus()
            return true
        } else if (notHeader.isEmpty()) {
            etNoteHeader.error = "Header is required"
            etNoteHeader.requestFocus()
            return true
        }
        return false
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}