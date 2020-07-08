package com.aslanovaslan.mynotesapplication.ui.adapter

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.aslanovaslan.mynotesapplication.R
import com.aslanovaslan.mynotesapplication.db.Note
import com.aslanovaslan.mynotesapplication.ui.internal.EventbusSender
import com.aslanovaslan.mynotesapplication.ui.view.AddNoteFragmentDirections
import com.aslanovaslan.mynotesapplication.ui.view.HomeFragmentDirections
import kotlinx.android.synthetic.main.note_layout.view.*
import org.greenrobot.eventbus.EventBus

class NotesAdapter(private val nots: List<Note>) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {


    class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.note_layout, parent, false)
        )
    }

    override fun getItemCount() = nots.count()

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.itemView.text_view_title.text = nots[position].title.toString()
        holder.itemView.text_view_note.text = nots[position].note.toString()
        holder.itemView.setOnClickListener {
            val actionArg = HomeFragmentDirections.actionGoWriteNotes(nots[position])
            Navigation.findNavController(it).navigate(actionArg)
            // EventBus.getDefault().postSticky(EventbusSender.SendUpdatedNote(nots[position]))
        }
        /*holder.itemView.setOnLongClickListener(View.OnLongClickListener { v: View? ->
           return fa
        })*/

    }
}