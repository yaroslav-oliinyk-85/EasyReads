package com.oliinyk.yaroslav.easyreads.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.RawQuery
import androidx.room.Update
import androidx.room.Upsert
import androidx.sqlite.db.SupportSQLiteQuery
import com.oliinyk.yaroslav.easyreads.data.local.entety.BookEntity
import kotlinx.coroutines.flow.Flow
import java.util.UUID

@Dao
interface BookDao {
    @RawQuery(observedEntities = [BookEntity::class])
    fun getAllSortedBy(query: SupportSQLiteQuery): Flow<List<BookEntity>>

    @Query("SELECT * FROM books")
    suspend fun getAll(): List<BookEntity>

    @Query("SELECT * FROM books")
    fun getAllAsFlow(): Flow<List<BookEntity>>

    @Query("SELECT * FROM books WHERE id = (:id)")
    fun getById(id: UUID): Flow<BookEntity?>

    @Query("SELECT DISTINCT author FROM books")
    fun getAuthors(): Flow<List<String>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(book: BookEntity)

    @Update
    suspend fun update(book: BookEntity)

    @Upsert
    suspend fun upsertAll(books: List<BookEntity>)

    @Delete
    suspend fun remove(book: BookEntity)
}
