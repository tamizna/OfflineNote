package com.tamizna.offlinenote.ui.folder

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.tamizna.offlinenote.R
import com.tamizna.offlinenote.database.AppDatabaseHelper
import com.tamizna.offlinenote.database.models.Folder
import com.tamizna.offlinenote.database.models.Note
import com.tamizna.offlinenote.databinding.ActivityCreateNewFolderBinding
import com.tamizna.offlinenote.thread.AppExecutors

class CreateNewFolderActivity : AppCompatActivity() {

    private lateinit var binding: ActivityCreateNewFolderBinding
    private var type = ""
    private var idFolder = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityCreateNewFolderBinding.inflate(layoutInflater)
        setContentView(binding.root)

        type = intent.getStringExtra("type")!!

        if (type == "edit") {
            idFolder = intent.getIntExtra("id_folder", 0)
            binding.btnCreate.text = getString(R.string.simpan)
            getNameFolder()
        }

        initListener()
    }

    private fun initListener() {
        binding.btnCreate.setOnClickListener {
            if (type == "create") {
                AppExecutors.getInstance().diskIO().execute {
                    AppDatabaseHelper.getInstance(this).folderDao().insertFolder(
                        Folder(
                            0,
                            binding.edtFolderName.text.toString()
                        )
                    ).run {
                        runOnUiThread {
                            Toast.makeText(
                                this@CreateNewFolderActivity,
                                "Berhasil membuat folder",
                                Toast.LENGTH_SHORT
                            ).show()
                            finish()
                        }
                    }
                }
            } else {
                AppExecutors.getInstance().diskIO().execute {
                    AppDatabaseHelper.getInstance(this).folderDao()
                        .updateFolder(Folder(idFolder, binding.edtFolderName.text.toString())).run {
                            runOnUiThread {
                                Toast.makeText(
                                    this@CreateNewFolderActivity,
                                    "Berhasil mengubah nama folder",
                                    Toast.LENGTH_SHORT
                                ).show()
                                finish()
                            }
                        }
                }
            }
        }
    }

    private fun getNameFolder() {
        var folder = Folder()

        AppExecutors.getInstance().diskIO().execute {
            folder = AppDatabaseHelper.getInstance(this).folderDao().getFolderById(idFolder)
        }

        AppExecutors.getInstance().mainThread().execute {
            binding.edtFolderName.setText(folder.judul)
        }
    }
}