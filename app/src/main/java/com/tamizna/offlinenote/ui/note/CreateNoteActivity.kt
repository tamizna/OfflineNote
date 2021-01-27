package com.tamizna.offlinenote.ui.note

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tamizna.offlinenote.database.AppDatabaseHelper
import com.tamizna.offlinenote.database.models.Note
import com.tamizna.offlinenote.databinding.ActivityCreateNoteBinding
import com.tamizna.offlinenote.thread.AppExecutors

class CreateNoteActivity : AppCompatActivity() {

    private var idFolder = 0
    private var idNote = 0
    private var type = ""
    private lateinit var binding: ActivityCreateNoteBinding
    private var note: Note = Note()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCreateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        idFolder = intent.getIntExtra("id_folder", 0)
        type = intent.getStringExtra("type")!!

        if (type == "edit") {
            idNote = intent.getIntExtra("id_note", 0)
            binding.btnSave.text = "Update"
            getNote()
        }

        initListener()
    }

    private fun getNote() {
        AppExecutors.getInstance().diskIO().execute {
            note = AppDatabaseHelper.getInstance(this).noteDao().getNoteById(idNote)
        }

        AppExecutors.getInstance().mainThread().execute {
            binding.edtNoteTitle.setText(note.judul)
            binding.edtNoteBody.setText(note.isi)
        }
    }

    private fun initListener() {
        binding.btnSave.setOnClickListener {
            if (dataValidation()) {
                if (type == "create") {
                    // SAVE
                    saveNote()
                } else {
                    updateNote()
                }
            } else {
                Toast.makeText(
                    this,
                    "Lengkapi judul & isi note terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun updateNote() {
        AppExecutors.getInstance().diskIO().execute {
            AppDatabaseHelper.getInstance(this).noteDao()
                .updateNote(
                    Note(
                        idNote,
                        binding.edtNoteTitle.text.toString(),
                        binding.edtNoteBody.text.toString(),
                        note.idFolder
                    )
                ).run {
                    runOnUiThread {
                        Toast.makeText(
                            this@CreateNoteActivity,
                            "Berhasil mengubah note",
                            Toast.LENGTH_SHORT
                        ).show()
                        finish()
                    }
                }
        }
    }

    private fun dataValidation(): Boolean {
        return !(binding.edtNoteTitle.text.isNullOrEmpty() && binding.edtNoteBody.text.isNullOrEmpty())
    }

    private fun saveNote() {
        AppExecutors.getInstance().diskIO().execute {
            AppDatabaseHelper.getInstance(this).noteDao().insertNote(
                Note(
                    0,
                    binding.edtNoteTitle.text.toString(),
                    binding.edtNoteBody.text.toString(),
                    idFolder
                )
            ).run {
                runOnUiThread {
                    Toast.makeText(
                        this@CreateNoteActivity,
                        "Berhasil menyimpan note",
                        Toast.LENGTH_SHORT
                    ).show()
                    finish()
                }
            }
        }
    }
}