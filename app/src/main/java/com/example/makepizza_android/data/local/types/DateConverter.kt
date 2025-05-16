package com.example.makepizza_android.data.local.types

import androidx.room.TypeConverter
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.TimeZone

class DateConverter {
    private val formatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US).apply {
        timeZone = TimeZone.getTimeZone("UTC")
    }

    @TypeConverter
    fun from(date: Date?): String? {
        return date?.let { formatter.format(it) }
    }

    @TypeConverter
    fun to(dateString: String?): Date? {
        return dateString?.let { formatter.parse(it) }
    }
}
