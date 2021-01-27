package com.tamizna.offlinenote.ui.folder

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.tamizna.offlinenote.R
import com.tamizna.offlinenote.database.AppDatabaseHelper
import com.tamizna.offlinenote.database.models.Folder
import com.tamizna.offlinenote.databinding.ActivityMainBinding
import com.tamizna.offlinenote.helper.SpaceItemDecoration
import com.tamizna.offlinenote.thread.AppExecutors
import com.tamizna.offlinenote.ui.UserSettingActivity
import com.tamizna.offlinenote.ui.note.ListNoteActivity

class MainActivity : AppCompatActivity() {
    private lateinit var folderAdapter: FolderAdapter
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val onItemClick = { folder: Folder ->
            val intent = Intent(this, ListNoteActivity::class.java)
            intent.putExtra("id_folder", folder.id)
            startActivity(intent)
        }

        folderAdapter = FolderAdapter(onItemClick)

        binding.rvFolderNote.run {
            clipToPadding = false
            layoutManager = GridLayoutManager(this@MainActivity, 2)
            addItemDecoration(SpaceItemDecoration(20))
            adapter = folderAdapter
        }

        getFolders()
        initListener()
    }

    private fun initListener() {
        binding.fabCreateFolder.setOnClickListener {
            val intent = Intent(this, CreateNewFolderActivity::class.java)
            intent.putExtra("type", "create")
            startActivity(intent)
        }
    }

    private fun getFolders() {
        AppExecutors.getInstance().diskIO().execute {
            AppDatabaseHelper.getInstance(this).folderDao().getAllFolder().map {
                Folder(
                    it.id,
                    it.judul
                )
            }.run {
                AppExecutors.getInstance().mainThread().execute {
                    folderAdapter.loadData(this)
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        getFolders()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_main, menu)

        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when(item.itemId) {
            R.id.action_settings -> {
                startActivity(Intent(this, UserSettingActivity::class.java))
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }
}