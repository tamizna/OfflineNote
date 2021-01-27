package com.tamizna.offlinenote.database.dao

import androidx.room.*
import com.tamizna.offlinenote.database.models.Folder

@Dao
interface FolderDao {
    @Query("SELECT * FROM folder")
    fun getAllFolder() : List<Folder>

    @Query("SELECT * FROM folder where id = (:id)")
    fun getFolderById(id : Int) : Folder

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertFolder(folder : Folder)

    @Update
    fun updateFolder(folder: Folder)

    @Delete
    fun deleteFolder(folder: Folder)
}