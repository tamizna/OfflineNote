package com.tamizna.offlinenote.database.dao

import androidx.room.*
import com.tamizna.offlinenote.database.models.Note

@Dao
interface NoteDao {
    @Query("SELECT * FROM note WHERE id_folder =(:idFolder)")
    fun getAllNote(idFolder: Int): List<Note>

    @Query("SELECT * FROM note where id = (:id)")
    fun getNoteById(id: Int): Note

    @Insert(onConflict = OnConflictStrategy.ABORT)
    fun insertNote(note: Note)

    @Update
    fun updateNote(note: Note)

    @Delete
    fun deleteNote(note: Note)
}