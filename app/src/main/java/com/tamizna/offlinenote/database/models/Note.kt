package com.tamizna.offlinenote.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.ForeignKey.CASCADE
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note",
foreignKeys = [ForeignKey(entity = Folder::class, parentColumns = ["id"], childColumns = ["id_folder"], onDelete = CASCADE)])
data class Note constructor(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Int=0,

    @ColumnInfo(name = "judul")
    val judul: String="",

    @ColumnInfo(name = "isi")
    val isi: String="",

    @ColumnInfo(name = "id_folder")
    val idFolder: Int=0
) : Serializable