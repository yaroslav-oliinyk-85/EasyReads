package com.oliinyk.yaroslav.easyreads.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import com.oliinyk.yaroslav.easyreads.data.local.entety.NoteEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface NoteDao {
    @Query("SELECT * FROM notes")
    fun getAll(): List<NoteEntity>

    @Query("SELECT * FROM notes WHERE book_id = :bookId ORDER BY added_date DESC")
    fun getAllByBookId(bookId: UUID): Flow<List<NoteEntity>>

    @Query("SELECT * FROM notes WHERE book_id = :bookId ORDER BY added_date DESC LIMIT 1")
    fun getLastAddedByBookId(bookId: UUID): Flow<NoteEntity?>

    @Insert
    suspend fun insert(note: NoteEntity)

    @Update
    suspend fun update(note: NoteEntity)

    @Upsert
    suspend fun upsertAll(notes: List<NoteEntity>)

    @Delete
    suspend fun delete(note: NoteEntity)

    @Delete
    suspend fun delete(notes: List<NoteEntity>)
}
