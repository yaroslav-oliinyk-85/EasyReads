package com.oliinyk.yaroslav.easyreads.ui.screen.readingsession.addeditdialog

import android.os.Parcelable
import kotlinx.parcelize.Parcelize
import java.time.LocalDateTime

@Parcelize
data class ReadingSessionAddEditUiStateDialog(
    val startPage: Int = 0,
    val endPage: Int = 0,
    val readPages: Int = 0,
    val hours: Int = 0,
    val minutes: Int = 0,
    val seconds: Int = 0,
    val startedAt: LocalDateTime = LocalDateTime.now(),
    val startPageReadOnly: Boolean = true,
    val startPageInputErrorMessage: String = "",
    val endPageInputErrorMessage: String = "",
) : Parcelable
