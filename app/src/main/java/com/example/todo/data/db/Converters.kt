package com.example.todo.data.db

import androidx.room.TypeConverter
import java.util.*

class Converters {
    @TypeConverter
    fun fromDate(date: Date): Long {
        return date.time
    }
    @TypeConverter
    fun toDate(milli: Long) : Date {
        return Date(milli)
    }

}