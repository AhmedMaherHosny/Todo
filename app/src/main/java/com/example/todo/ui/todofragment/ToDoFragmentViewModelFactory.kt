package com.example.todo.ui.todofragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todo.data.repositories.ToDoRepository

class ToDoFragmentViewModelFactory(private val repository: ToDoRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ToDoFragmentViewModel(repository) as T
    }
}
