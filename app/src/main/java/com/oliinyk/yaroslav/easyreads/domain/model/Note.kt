package com.oliinyk.yaroslav.easyreads.domain.model

import android.os.Parcelable
import com.oliinyk.yaroslav.easyreads.data.local.entety.NoteEntity
import com.oliinyk.yaroslav.easyreads.domain.serializer.UUIDSerializer
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID

@Serializable
@Parcelize
data class Note(
    @Serializable(with = UUIDSerializer::class)
    override val id: UUID = UUID.randomUUID(),
    @Serializable(with = UUIDSerializer::class)
    val bookId: UUID? = null,
    val text: String = "",
    val page: Int? = null,
    @Contextual
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
