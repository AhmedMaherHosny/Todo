package com.example.todo.ui.todoviewfragment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todo.data.repositories.ToDoRepository

class ToDoViewFragmentViewModelFactory(
    private val repository: ToDoRepository,
    private val args: ToDoViewFragmentArgs
): ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return ToDoViewFragmentViewModel(repository, args) as T
    }

}