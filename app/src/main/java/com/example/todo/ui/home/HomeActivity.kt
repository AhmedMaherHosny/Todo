package com.example.todo.ui.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.todo.R
import com.example.todo.databinding.ActivityHomeBinding
import com.example.todo.ui.bottomsheet.BottomSheetFragment
import com.example.todo.ui.settingfragment.SettingFragment
import com.example.todo.ui.todofragment.ToDoFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Todo)
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(1).isEnabled = false
        val navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNavigationView.setupWithNavController(navController)
        binding.floatingAdd.setOnClickListener{
            navController.navigate(R.id.bottomSheetFragment)
        }
        if (navController.currentDestination?.id == R.id.toDoViewFragment){
            Log.e("viewfragment : ", true.toString())
        }else{
            Log.e("current : ", navController.currentDestination?.id.toString())
        }
        binding.coordinator.isVisible = navController.currentDestination?.id != R.id.toDoViewFragment
    }
}