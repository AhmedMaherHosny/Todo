package com.example.todo.other

import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.view.isVisible
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.data.db.entities.ToDoItem
import com.example.todo.databinding.TodoItemBinding

class ToDoListAdapter(var TodoList: MutableList<ToDoItem>?) :
    RecyclerView.Adapter<ToDoListAdapter.ToDoViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ToDoViewHolder {
        val binding = TodoItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ToDoViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ToDoViewHolder, position: Int) {
        val todoItem = TodoList!![position]
        holder.bind(todoItem)
        holder.itemView.setOnClickListener {
            onItemClickAdapter?.onItemClick(position, todoItem)
        }
        holder.binding.btnCheck.setOnClickListener {
            onItemClickAdapter?.onBtnClick(position, todoItem)
            Log.e("btnlistner : ", onItemClickAdapter.toString())
        }
    }

    override fun getItemCount(): Int = TodoList?.size ?: 0

    var onItemClickAdapter: OnItemClickAdapter? = null

    interface OnItemClickAdapter {
        fun onItemClick(position: Int, item: ToDoItem)
        fun onBtnClick(position: Int, item: ToDoItem)
    }


    class ToDoViewHolder(val binding: TodoItemBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(todoItem: ToDoItem) {
            if (todoItem.isDone) {
                binding.line.setBackgroundResource(R.drawable.linedone)
                binding.todoLabel.setTextColor(Color.parseColor("#61e757"))
                binding.todoLabel.text = todoItem.label
                binding.time.text = todoItem.time
                binding.btnCheck.isVisible = false
                binding.labeldone.isVisible = true
            } else {
                binding.todoLabel.text = todoItem.label
                binding.time.text = todoItem.time
                binding.line.setBackgroundResource(R.drawable.line)
                binding.todoLabel.setTextColor(Color.parseColor("#5d9cec"))
                binding.labeldone.isVisible = false
                binding.btnCheck.isVisible = true
            }
        }
    }
}