package com.oliinyk.yaroslav.easyreads.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
enum class BookShelvesType : Parcelable {
    WANT_TO_READ, READING, FINISHED
}