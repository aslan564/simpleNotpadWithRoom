package com.aslanovaslan.mynotesapplication.ui.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.Navigation
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.aslanovaslan.mynotesapplication.R
import com.aslanovaslan.mynotesapplication.db.Note
import com.aslanovaslan.mynotesapplication.db.NoteDatabase
import com.aslanovaslan.mynotesapplication.ui.adapter.NotesAdapter
import com.aslanovaslan.mynotesapplication.ui.internal.BaseFragment
import kotlinx.android.synthetic.main.fragment_home.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class HomeFragment : BaseFragment() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
       // createNewNote.setOnLongClickListener()
        createNewNote.setOnClickListener {
            val action=
                HomeFragmentDirections.actionGoWriteNotes()
            Navigation.findNavController(it).navigate(action)
        }
        noteRecyclerView.setHasFixedSize(true)
        noteRecyclerView.layoutManager=StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)
        launch (Dispatchers.IO){
            context?.let {
              val newlistNote = getAllNoteListFromDatabase()
                withContext(Dispatchers.Main){
                    noteRecyclerView.adapter=NotesAdapter(newlistNote)
                    updateActionBarTitle()
                }
            }
        }
    }

    private fun updateActionBarTitle() {
        (activity as? AppCompatActivity)?.supportActionBar?.title = "All Notes"
    }

    private suspend fun getAllNoteListFromDatabase():List<Note> {
        return NoteDatabase(requireContext()).getNoteDao().getAllNotes()
    }

}