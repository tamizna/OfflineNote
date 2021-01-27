package com.tamizna.offlinenote.ui.note

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.tamizna.offlinenote.R
import com.tamizna.offlinenote.database.AppDatabaseHelper
import com.tamizna.offlinenote.database.models.Note
import com.tamizna.offlinenote.databinding.ActivityDetailNoteBinding
import com.tamizna.offlinenote.thread.AppExecutors

class DetailNoteActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailNoteBinding
    private lateinit var note: Note
    private var idNote = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idNote = intent.getIntExtra("id_note", 0)

        getNoteData()
    }

    private fun getNoteData() {
        AppExecutors.getInstance().diskIO().execute {
            note = AppDatabaseHelper.getInstance(this).noteDao().getNoteById(idNote)
        }

        AppExecutors.getInstance().mainThread().execute {
            binding.txtTitleNote.text = note.judul
            binding.txtBodyNote.text = note.isi
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_note, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_edit -> {
                // EDIT FOLDER
                val intent = Intent(this, CreateNoteActivity::class.java)
                intent.putExtra("type", "edit")
                intent.putExtra("id_note", idNote)
                startActivity(intent)
                return true
            }
            R.id.action_delete -> {
                // DELETE NOTE
                deleteNote()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private fun deleteNote() {
        AppExecutors.getInstance().diskIO().execute {
            AppDatabaseHelper.getInstance(this).noteDao().deleteNote(Note(id = idNote))
        }
        finish()
    }

    override fun onResume() {
        super.onResume()
        getNoteData()
    }
}