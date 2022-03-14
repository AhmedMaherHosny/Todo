package com.example.todo.ui.bottomsheet

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.todo.data.repositories.ToDoRepository

class BottomSheetViewModelFactory(private val repository: ToDoRepository) : ViewModelProvider.NewInstanceFactory() {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return BottomSheetViewModel(repository) as T
    }
}