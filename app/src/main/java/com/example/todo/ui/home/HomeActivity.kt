package com.example.todo.ui.home

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.todo.databinding.ActivityHomeBinding
import com.example.todo.ui.bottomsheet.BottomSheetFragment
import com.prolificinteractive.materialcalendarview.CalendarDay

class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityHomeBinding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(1).isEnabled = false
        binding.calendarView.setDateSelected(CalendarDay.today(), true)
        binding.floatingAdd.setOnClickListener{
            val bottomSheet = BottomSheetFragment()
            bottomSheet.show(supportFragmentManager, "")
        }
    }
}