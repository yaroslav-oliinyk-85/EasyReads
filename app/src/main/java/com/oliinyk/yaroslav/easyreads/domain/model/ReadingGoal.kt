package com.oliinyk.yaroslav.easyreads.domain.model

import android.os.Parcelable
import com.oliinyk.yaroslav.easyreads.data.local.entety.ReadingGoalEntity
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import java.time.LocalDate

@Serializable
@Parcelize
data class ReadingGoal(
    val year: Int = LocalDate.now().year,
    val goal: Int = 0,
) : Parcelable

fun ReadingGoal.toEntity(): ReadingGoalEntity =
    ReadingGoalEntity(
        year = year,
        goal = goal,
    )
