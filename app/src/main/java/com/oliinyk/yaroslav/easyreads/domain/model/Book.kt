package com.oliinyk.yaroslav.easyreads.domain.model

import android.os.Parcelable
import com.oliinyk.yaroslav.easyreads.data.local.entety.BookEntity
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID

@Parcelize
data class Book(
    override val id: UUID = UUID.randomUUID(),
    val title: String = "",
    val author: String = "",
    val description: String = "",
    val isbn: String = "",
    val shelf: BookShelvesType = BookShelvesType.WANT_TO_READ,
    val pageAmount: Int = 0,
    val pageCurrent: Int = 0,
    val addedAt: LocalDateTime = LocalDateTime.now(),
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val finishedAt: LocalDateTime? = null,
    val isFinished: Boolean = false,
    val coverImageFileName: String? = null,
) : BaseModel(),
    Parcelable

fun Book.toEntity(): BookEntity =
    BookEntity(
        id = id,
        title = title,
        author = author,
        isbn = isbn,
        description = description,
        shelve = shelf.toString(),
        pageAmount = pageAmount,
        pageCurrent = pageCurrent,
        addedAt = addedAt.atZone(ZoneId.systemDefault()).toInstant(),
        updatedAt = updatedAt.atZone(ZoneId.systemDefault()).toInstant(),
        finishedAt = finishedAt?.atZone(ZoneId.systemDefault())?.toInstant(),
        isFinished = isFinished.toString(),
        coverImageFileName = coverImageFileName,
    )
