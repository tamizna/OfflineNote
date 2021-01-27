package com.tamizna.offlinenote.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.tamizna.offlinenote.database.dao.FolderDao
import com.tamizna.offlinenote.database.dao.NoteDao
import com.tamizna.offlinenote.database.models.Folder
import com.tamizna.offlinenote.database.models.Note

@Database(entities = [Folder::class, Note::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun folderDao() : FolderDao
    abstract fun noteDao() : NoteDao
}