package com.oliinyk.yaroslav.easyreads.data.local.entety

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.oliinyk.yaroslav.easyreads.domain.model.Book
import com.oliinyk.yaroslav.easyreads.domain.model.BookShelvesType
import java.time.Instant
import java.time.ZoneId
import java.util.UUID

@Entity("books")
data class BookEntity(
    @PrimaryKey
    val id: UUID,
    val title: String,
    val author: String,
    val description: String,
    @ColumnInfo(name = "isbn", defaultValue = "")
    val isbn: String = "",
    @ColumnInfo(defaultValue = "WANT_TO_READ")
    val shelve: String,
    @ColumnInfo("page_amount")
    val pageAmount: Int,
    @ColumnInfo("page_current")
    val pageCurrent: Int,
    @ColumnInfo("added_date")
    val addedAt: Instant,
    @ColumnInfo("updated_date")
    val updatedAt: Instant,
    @ColumnInfo("finished_date")
    val finishedAt: Instant? = null,
    @ColumnInfo(name = "is_finished", defaultValue = "FALSE")
    val isFinished: String,
    @ColumnInfo("read_minutes_count", defaultValue = "0")
    val readMinutesCount: Int = 0, // to remove
    @ColumnInfo("read_sessions_count", defaultValue = "0")
    val readSessionsCount: Int = 0, // to remove
    @ColumnInfo("cover_image_file_name")
    val coverImageFileName: String? = null,
)

fun BookEntity.toModel(): Book =
    Book(
        id = id,
        title = title,
        author = author,
        isbn = isbn,
        description = description,
        shelf = BookShelvesType.valueOf(shelve),
        pageAmount = pageAmount,
        pageCurrent = pageCurrent,
        addedAt = addedAt.atZone(ZoneId.systemDefault()).toLocalDateTime(),
        updatedAt = updatedAt.atZone(ZoneId.systemDefault()).toLocalDateTime(),
        finishedAt = finishedAt?.atZone(ZoneId.systemDefault())?.toLocalDateTime(),
        isFinished = isFinished.toBoolean(),
        coverImageFileName = coverImageFileName,
    )
