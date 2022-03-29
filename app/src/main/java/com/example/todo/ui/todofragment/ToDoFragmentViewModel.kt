package com.example.todo.ui.todofragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.db.entities.ToDoItem
import com.example.todo.data.repositories.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ToDoFragmentViewModel(private val repository: ToDoRepository) : ViewModel() {
    fun getAllTodoItems() = repository.getAllToDoItems()
    fun getToDoByDate(date: Date) = repository.getToDoByDate(date)
    suspend fun markDone(id:Int, done:Boolean) = repository.markDone(id, done)
    suspend fun delete(item:ToDoItem) = repository.delete(item)
}