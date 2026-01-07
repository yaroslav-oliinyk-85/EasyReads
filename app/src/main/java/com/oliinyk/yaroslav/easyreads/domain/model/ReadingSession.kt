package com.oliinyk.yaroslav.easyreads.domain.model

import android.os.Parcelable
import com.oliinyk.yaroslav.easyreads.data.local.entety.ReadingSessionEntity
import com.oliinyk.yaroslav.easyreads.domain.serializer.UUIDSerializer
import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants.MILLISECONDS_IN_ONE_SECOND
import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants.MINUTES_IN_ONE_HOUR
import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants.SECONDS_IN_ONE_HOUR
import com.oliinyk.yaroslav.easyreads.domain.util.AppConstants.SECONDS_IN_ONE_MINUTE
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Contextual
import kotlinx.serialization.Serializable
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.UUID
import kotlin.math.roundToInt

@Serializable
@Parcelize
data class ReadingSession(
    @Serializable(with = UUIDSerializer::class)
    override val id: UUID = UUID.randomUUID(),
    @Serializable(with = UUIDSerializer::class)
    val bookId: UUID? = null,
    @Contextual
    val startedAt: LocalDateTime = LocalDateTime.now(),
    @Contextual
    val updatedAt: LocalDateTime = LocalDateTime.now(),
    val readTimeInMilliseconds: Long = 0,
    val startPage: Int = 0,
    val endPage: Int = 0,
    val readPages: Int = 0,
    val recordStatus: ReadingSessionRecordStatusType = ReadingSessionRecordStatusType.STARTED,
) : BaseModel(),
    Parcelable {
    private val readTotalSeconds: Int
        get() = (readTimeInMilliseconds / MILLISECONDS_IN_ONE_SECOND).toInt()
    private val readTotalMinutes: Int
        get() = readTotalSeconds / SECONDS_IN_ONE_MINUTE

    val readHours: Int
        get() = readTotalMinutes / MINUTES_IN_ONE_HOUR
    val readMinutes: Int
        get() = readTotalMinutes % MINUTES_IN_ONE_HOUR
    val readSeconds: Int
        get() = readTotalSeconds % SECONDS_IN_ONE_MINUTE

    val readPagesHour: Int
        get() =
            if (readTotalSeconds != 0) {
                (readPages.toDouble() / readTotalSeconds.toDouble() * SECONDS_IN_ONE_HOUR).roundToInt()
            } else {
                0
            }

    companion object {
        fun toReadTimeInMilliseconds(
            hours: Int,
            minutes: Int,
            seconds: Int,
        ): Long {
            val readHours = hours * SECONDS_IN_ONE_HOUR
            val readMinutes = minutes * SECONDS_IN_ONE_MINUTE
            val readSeconds = seconds
            return (readHours + readMinutes + readSeconds).toLong() * MILLISECONDS_IN_ONE_SECOND
        }
    }
}

fun ReadingSession.toEntity(): ReadingSessionEntity =
    ReadingSessionEntity(
        id = id,
        bookId = bookId,
        startedAt = startedAt.atZone(ZoneId.systemDefault()).toInstant(),
        updatedAt = updatedAt.atZone(ZoneId.systemDefault()).toInstant(),
        readTimeInMilliseconds = readTimeInMilliseconds,
        endPage = endPage,
        startPage = startPage,
        readPages = readPages,
        recordStatus = recordStatus.toString(),
    )
