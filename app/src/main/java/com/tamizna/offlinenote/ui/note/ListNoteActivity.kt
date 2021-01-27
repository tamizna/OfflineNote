package com.tamizna.offlinenote.ui.note

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.tamizna.offlinenote.R
import com.tamizna.offlinenote.database.AppDatabaseHelper
import com.tamizna.offlinenote.database.models.Folder
import com.tamizna.offlinenote.database.models.Note
import com.tamizna.offlinenote.databinding.ActivityListNoteBinding
import com.tamizna.offlinenote.helper.SpaceItemDecoration
import com.tamizna.offlinenote.thread.AppExecutors
import com.tamizna.offlinenote.ui.folder.CreateNewFolderActivity
import com.tamizna.offlinenote.ui.folder.FolderAdapter

class ListNoteActivity : AppCompatActivity() {
    var idFolder = 0

    private lateinit var binding: ActivityListNoteBinding
    private lateinit var noteAdapter: NoteAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        idFolder = intent.getIntExtra("id_folder", 0)
        supportActionBar?.title = "List Note"

        binding = ActivityListNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val onItemClick = { note: Note ->
            val intent = Intent(this, DetailNoteActivity::class.java)
            intent.putExtra("id_note", note.id)
            startActivity(intent)
        }

        noteAdapter = NoteAdapter(onItemClick)

        binding.rvNotes.run {
            clipToPadding = false
            layoutManager = LinearLayoutManager(this@ListNoteActivity)
            addItemDecoration(SpaceItemDecoration(20))
            adapter = noteAdapter
        }

        getNoteByFolder()
        initListener()
    }

    private fun initListener() {
        binding.fabCreateNote.setOnClickListener {
            val intent = Intent(this, CreateNoteActivity::class.java)
            intent.putExtra("type", "create")
            intent.putExtra("id_folder", idFolder)
            startActivity(intent)
        }
    }

    private fun getNoteByFolder() {
        AppExecutors.getInstance().diskIO().execute {
            AppDatabaseHelper.getInstance(this).noteDao().getAllNote(idFolder).map {
                Note(
                    it.id,
                    it.judul, it.isi, it.idFolder
                )
            }.run {
                AppExecutors.getInstance().mainThread().execute {
                     noteAdapter.loadData(this)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getNoteByFolder()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_folder, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_edit -> {
                // EDIT FOLDER
                val intent = Intent(this, CreateNewFolderActivity::class.java)
                intent.putExtra("type", "edit")
                intent.putExtra("id_folder", idFolder)
                startActivity(intent)
                return true
            }
            R.id.action_delete -> {
                // DELETE FOLDER
                AppExecutors.getInstance().diskIO().execute {
                    AppDatabaseHelper.getInstance(this).folderDao().deleteFolder(Folder(id = idFolder))
                }
                finish()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}