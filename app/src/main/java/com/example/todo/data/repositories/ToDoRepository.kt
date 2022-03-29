package com.example.todo.data.repositories

import com.example.todo.data.db.ToDoDatabase
import com.example.todo.data.db.entities.ToDoItem
import java.util.*

class ToDoRepository(private val db: ToDoDatabase) {
    suspend fun upSert(item: ToDoItem) = db.getToDoDAO().upSert(item)
    suspend fun delete(item: ToDoItem) = db.getToDoDAO().delete(item)
    suspend fun markDone(id: Int, done: Boolean) = db.getToDoDAO().markDone(id, done)
    fun getAllToDoItems() = db.getToDoDAO().getAllToDoItems()
    fun getToDoByDate(date: Date) = db.getToDoDAO().getToDoByDate(date)
}