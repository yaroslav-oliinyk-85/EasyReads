package com.oliinyk.yaroslav.easyreads.domain.model

import android.os.Parcelable
import com.oliinyk.yaroslav.easyreads.data.local.entety.NoteEntity
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID

@Parcelize
data class Note(
    override val id: UUID = UUID.randomUUID(),
    val bookId: UUID? = null,
    val text: String = "",
    val page: Int? = null,
    val addedAt: LocalDateTime = LocalDateTime.now(),
) : BaseModel(),
    Parcelable

fun Note.toEntity(): NoteEntity =
    NoteEntity(
        id = id,
        bookId = bookId,
        text = text,
        page = page,
        addedAt = addedAt.atZone(ZoneId.systemDefault()).toInstant(),
    )
