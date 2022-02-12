package com.example.todo.ui.todofragment

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.R
import com.example.todo.databinding.FragmentToDoBinding
import com.prolificinteractive.materialcalendarview.CalendarDay

class ToDoFragment : Fragment() {
    lateinit var binding : FragmentToDoBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentToDoBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.calendarView.setDateSelected(CalendarDay.today(), true)
    }
}