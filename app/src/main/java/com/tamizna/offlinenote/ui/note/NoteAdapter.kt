package com.tamizna.offlinenote.ui.note

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.tamizna.offlinenote.database.models.Folder
import com.tamizna.offlinenote.database.models.Note
import com.tamizna.offlinenote.databinding.ItemNoteBinding

class NoteAdapter(private val onItemClick: (Note) -> Unit) : RecyclerView.Adapter<NoteViewHolder>() {
    private val notes : MutableList<Note> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val binding = ItemNoteBinding.inflate(inflater, parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.binding.txtTitleNote.text = notes[position].judul
        holder.binding.txtBodyNote.text = notes[position].isi
        holder.binding.root.setOnClickListener {
            onItemClick(notes[position])
        }
    }

    override fun getItemCount(): Int = notes.size

    fun loadData(data : List<Note>) {
        notes.clear()
        notes.addAll(data)
        notifyDataSetChanged()
    }
}