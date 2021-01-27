package com.tamizna.offlinenote.database

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.tamizna.offlinenote.thread.AppExecutors

object AppDatabaseHelper {
    private var INSTANCE: AppDatabase? = null

    @Synchronized
    fun getInstance(context: Context): AppDatabase {
        if (INSTANCE == null) {
            INSTANCE = Room.databaseBuilder(context, AppDatabase::class.java, "note_app")
                .build()
        }
        return INSTANCE!!
    }
}