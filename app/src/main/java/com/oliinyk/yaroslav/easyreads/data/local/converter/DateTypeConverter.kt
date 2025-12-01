package com.oliinyk.yaroslav.easyreads.data.local.converter

import androidx.room.TypeConverter
import java.util.Date

class DateTypeConverter {
    @TypeConverter
    fun fromDate(date: Date): Long = date.time

    @TypeConverter
    fun toDate(millisSinceEpoch: Long): Date = Date(millisSinceEpoch)
}
