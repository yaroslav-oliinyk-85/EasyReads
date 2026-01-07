package com.oliinyk.yaroslav.easyreads.data.repository

import com.oliinyk.yaroslav.easyreads.data.local.dao.NoteDao
import com.oliinyk.yaroslav.easyreads.data.local.entety.toModel
import com.oliinyk.yaroslav.easyreads.di.AppCoroutineScope
import com.oliinyk.yaroslav.easyreads.di.DispatcherIO
import com.oliinyk.yaroslav.easyreads.domain.model.Note
import com.oliinyk.yaroslav.easyreads.domain.model.toEntity
import com.oliinyk.yaroslav.easyreads.domain.repository.NoteRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import java.util.UUID
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class NoteRepositoryImpl
    @Inject
    constructor(
        private val noteDao: NoteDao,
        @AppCoroutineScope private val coroutineScope: CoroutineScope,
        @DispatcherIO private val ioDispatcher: CoroutineDispatcher,
    ) : NoteRepository {
        override suspend fun getAll(): List<Note> =
            noteDao
                .getAll()
                .map { entity ->
                    entity.toModel()
                }

        override fun getAllByBookId(bookId: UUID): Flow<List<Note>> =
            noteDao
                .getAllByBookId(bookId)
                .map { entities ->
                    entities.map { it.toModel() }
                }

        override fun getLastAddedByBookId(bookId: UUID): Flow<Note?> =
            noteDao
                .getLastAddedByBookId(bookId)
                .map { it?.toModel() }
                .distinctUntilChanged()

        override fun save(note: Note) {
            coroutineScope.launch(ioDispatcher) { noteDao.insert(note.toEntity()) }
        }

        override fun saveAll(notes: List<Note>) {
            coroutineScope.launch(ioDispatcher) {
                noteDao.upsertAll(notes.map { it.toEntity() })
            }
        }

        override fun update(note: Note) {
            coroutineScope.launch(ioDispatcher) { noteDao.update(note.toEntity()) }
        }

        override fun remove(note: Note) {
            coroutineScope.launch(ioDispatcher) { noteDao.delete(note.toEntity()) }
        }

        override fun remove(notes: List<Note>) {
            if (notes.isEmpty()) {
                return
            }
            coroutineScope.launch(ioDispatcher) { noteDao.delete(notes.map { it.toEntity() }) }
        }
    }
