package com.example.todo.data.db.entities

import androidx.room.*
import java.util.*

@Entity
data class ToDoItem(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo
    val label: String,
    @ColumnInfo
    val description: String,
    @ColumnInfo
    val time: String,
    @ColumnInfo
    val date: Date,
    @ColumnInfo
    val isDone: Boolean
)
