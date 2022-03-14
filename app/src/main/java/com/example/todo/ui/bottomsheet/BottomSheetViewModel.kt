package com.example.todo.ui.bottomsheet

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import androidx.lifecycle.viewModelScope
import com.example.todo.data.db.entities.ToDoItem
import com.example.todo.data.repositories.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class BottomSheetViewModel(private val repository: ToDoRepository) : ViewModel() {
    val toDoName = ObservableField<String>()
    val toDoNameError = ObservableField<String>()
    val toDoDescription = ObservableField<String>()
    val toDoDescriptionError = ObservableField<String>()
    val isAdded = MutableLiveData<Boolean>()

    fun addToDo(time: String, calendar: Date) {
        if (validate()) {
            insertToDo(time, calendar)
        }
    }

    private fun insertToDo(time: String, calendar: Date) {
        val todo = ToDoItem(
            label = toDoName.get()!!,
            description = toDoDescription.get()!!,
            time = time,
            date = calendar,
            isDone = false
        )
        viewModelScope.launch(Dispatchers.IO) {
            repository.upSert(todo)
            isAdded.postValue(true)
        }
    }

    private fun validate(): Boolean {
        var valid = true
        if (toDoName.get().isNullOrBlank()) {
            toDoNameError.set("Please Enter Name...")
            valid = false
        } else {
            toDoNameError.set(null)
        }
        if (toDoDescription.get().isNullOrBlank()) {
            toDoDescriptionError.set("Please Enter Description...")
            valid = false
        } else {
            toDoDescriptionError.set(null)
        }
        return valid
    }
}