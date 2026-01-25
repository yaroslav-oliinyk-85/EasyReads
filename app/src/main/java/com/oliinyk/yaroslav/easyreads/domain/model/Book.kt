package com.oliinyk.yaroslav.easyreads.domain.model

import android.os.Parcelable
import com.oliinyk.yaroslav.easyreads.data.local.entety.BookEntity
import com.oliinyk.yaroslav.easyreads.domain.serializer.UUIDSerializer
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID

@Serializable
@Parcelize
data class Book(
    @Serializable(with = UUIDSerializer::class)
    override val id: UUID = UUID.randomUUID(),
    val title: String = "",
    val author: String = "",
    val description: String = "",
    val isbn: String = "",
    val shelf: BookShelvesType = BookShelvesType.WANT_TO_READ,
    @SerialName("pageAmount")
    val pagesCount: Int = 0,
    val pageCurrent: Int = 0,
    @Contextual
    val addedAt: LocalDateTime = LocalDateTime.now(),
    @Contextual
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    @Contextual
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
        pageAmount = pagesCount,
        pageCurrent = pageCurrent,
        addedAt = addedAt.atZone(ZoneId.systemDefault()).toInstant(),
        updatedAt = updatedAt.atZone(ZoneId.systemDefault()).toInstant(),
        finishedAt = finishedAt?.atZone(ZoneId.systemDefault())?.toInstant(),
        isFinished = isFinished.toString(),
        coverImageFileName = coverImageFileName,
    )
