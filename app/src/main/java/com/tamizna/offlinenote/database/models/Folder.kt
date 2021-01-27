package com.tamizna.offlinenote.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "folder")
data class Folder constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id : Int=0,

    @ColumnInfo(name = "judul")
    val judul : String=""
) : Serializable