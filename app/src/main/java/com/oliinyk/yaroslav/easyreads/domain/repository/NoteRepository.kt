package com.oliinyk.yaroslav.easyreads.domain.repository

import com.oliinyk.yaroslav.easyreads.domain.model.Note
import kotlinx.coroutines.flow.Flow
import java.util.UUID

interface NoteRepository {
    suspend fun getAll(): List<Note>

    fun getAllByBookId(bookId: UUID): Flow<List<Note>>

    fun getLastAddedByBookId(bookId: UUID): Flow<Note?>

    fun save(note: Note)

    fun saveAll(notes: List<Note>)

    fun update(note: Note)

    fun remove(note: Note)

    fun remove(notes: List<Note>)
}
