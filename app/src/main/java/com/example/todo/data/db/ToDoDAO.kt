package com.example.todo.data.db

import androidx.lifecycle.LiveData
import androidx.room.*
import com.example.todo.data.db.entities.ToDoItem
import java.util.*

@Dao
interface ToDoDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun upSert(item: ToDoItem)

    @Delete
    suspend fun delete(item: ToDoItem)

    @Query("SELECT * FROM ToDoItem")
    fun getAllToDoItems(): LiveData<MutableList<ToDoItem>>

    @Query("SELECT * FROM ToDoItem WHERE date=:date")
    fun getToDoByDate(date:Date) : LiveData<MutableList<ToDoItem>>
}