package com.example.todo.ui.todofragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.todo.data.db.ToDoDatabase
import com.example.todo.data.repositories.ToDoRepository
import com.example.todo.databinding.FragmentToDoBinding
import com.example.todo.other.ToDoListAdapter
import com.example.todo.other.clearTime
import com.prolificinteractive.materialcalendarview.CalendarDay
import java.util.*


class ToDoFragment : Fragment() {
    private lateinit var binding: FragmentToDoBinding
    lateinit var viewModel: ToDoFragmentViewModel
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentToDoBinding.inflate(layoutInflater)
        val dataBase = ToDoDatabase(requireContext())
        val repository = ToDoRepository(dataBase)
        val factory = ToDoFragmentViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[ToDoFragmentViewModel::class.java]
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    private val adapter = ToDoListAdapter(null)
    private var calendar: Calendar = Calendar.getInstance()

    @SuppressLint("NotifyDataSetChanged")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }


//    override fun onResume() {
//        super.onResume()
//        getToDoFromDB()
//    }


    private fun initView() {
        binding.todoRecycler.adapter = adapter
        binding.calendarView.setDateSelected(CalendarDay.today(), true)
        getToDoFromDB()
        binding.calendarView.setOnDateChangedListener { widget, date, selected ->
            calendar.set(Calendar.DAY_OF_MONTH, date.day)
            calendar.set(Calendar.MONTH, date.month - 1)
            calendar.set(Calendar.YEAR, date.year)
            Log.e("date : ", calendar.clearTime().toString())
            getToDoFromDB()
        }
//        viewModel.getAllTodoItems().observe(viewLifecycleOwner){
//            adapter.TodoList=it
//            adapter.notifyDataSetChanged()
//        }
    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getToDoFromDB() {
        viewModel.getToDoByDate(calendar.clearTime().time).observe(viewLifecycleOwner) {
            adapter.TodoList = it.toMutableList()
            adapter.notifyDataSetChanged()
        }
    }

}