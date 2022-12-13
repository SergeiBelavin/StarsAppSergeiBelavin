package com.example.retrofitoff.data.repository
import java.text.SimpleDateFormat
import java.util.*

object DateConverter {
    private val simpleDateFormat: SimpleDateFormat =
        SimpleDateFormat("EEE MMM dd HH:mm:ss zzz yyyy", Locale.ENGLISH)

    //@TypeConverter
    fun convertDateToTimestamp(date: Date?): String? {
        return date?.toString()
    }


    fun convertTimestampToDate(date: String?): Date? {
        return date?.let { simpleDateFormat.parse(it) }
    }
}