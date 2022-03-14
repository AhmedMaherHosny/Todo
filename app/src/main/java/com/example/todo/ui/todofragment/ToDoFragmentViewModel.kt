package com.example.todo.ui.todofragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.repositories.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ToDoFragmentViewModel(private val repository: ToDoRepository) : ViewModel() {
    fun getAllTodoItems() = repository.getAllToDoItems()
    fun getToDoByDate(date: Date) = repository.getToDoByDate(date)
}