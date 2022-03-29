package com.example.todo.ui.todoviewfragment

import androidx.databinding.ObservableField
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todo.data.db.entities.ToDoItem
import com.example.todo.data.repositories.ToDoRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*

class ToDoViewFragmentViewModel(
    private val repository: ToDoRepository,
    args: ToDoViewFragmentArgs
) : ViewModel() {
    val toDoName = ObservableField(args.label)
    val toDoNameError = ObservableField<String>()
    val toDoDescription = ObservableField(args.description)
    val toDoDescriptionError = ObservableField<String>()
    val isAdded = MutableLiveData<Boolean>()
    private val isDone = args.isDone
    private val ID = args.id
    fun updateToDo(time: String, calendar: Date) {
        if (validate()) {
            upsertToDo(time, calendar)
        }
    }

    private fun upsertToDo(time: String, calendar: Date) {
        val todo = ToDoItem(
            id = ID,
            label = toDoName.get()!!,
            description = toDoDescription.get()!!,
            time = time,
            date = calendar,
            isDone = isDone
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