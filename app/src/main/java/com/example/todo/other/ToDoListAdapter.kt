package com.example.todo.other

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.data.db.entities.ToDoItem
import com.example.todo.databinding.TodoItemBinding
import com.example.todo.ui.todofragment.ToDoFragmentViewModel

class ToDoListAdapter(var TodoList: MutableList<ToDoItem>?) :
    RecyclerView.Adapter<ToDoListAdapter.ToDoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val todoItem = TodoList!![position]
        holder.bind(todoItem)
    }

    override fun getItemCount(): Int = TodoList?.size ?: 0


    class ToDoViewHolder(private val binding: TodoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todoItem: ToDoItem) {
            binding.todoLabel.text = todoItem.label
            binding.time.text = todoItem.time
        }
    }
}