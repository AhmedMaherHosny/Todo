package com.example.todo.other

import java.util.*

fun Calendar.clearTime(): Calendar {
    this.clear(Calendar.HOUR)
    this.clear(Calendar.HOUR_OF_DAY)
    this.clear(Calendar.AM_PM)
    this.clear(Calendar.MINUTE)
    this.clear(Calendar.SECOND)
    this.clear(Calendar.MILLISECOND)
    return this
}