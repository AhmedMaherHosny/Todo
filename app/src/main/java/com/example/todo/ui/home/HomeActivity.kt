package com.example.todo.ui.home

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.todo.R
import com.example.todo.databinding.ActivityHomeBinding
import kotlinx.coroutines.*


class HomeActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHomeBinding
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        setTheme(R.style.Theme_Todo)
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(1).isEnabled = false
        navController = findNavController(R.id.nav_host_fragment)
        binding.bottomNavigationView.setupWithNavController(navController)
        binding.floatingAdd.setOnClickListener {
            navController.navigate(R.id.bottomSheetFragment)
        }

        chickHideOrNoBottomNavigationLooper(1)

    }

    private fun chickHideOrNoBottomNavigationLooper(timeInterval: Long): Job {
        return CoroutineScope(Dispatchers.Default).launch {
            while (isActive) {
                withContext(Dispatchers.Main) {
                    binding.coordinator.isVisible =
                        navController.currentDestination?.id != R.id.toDoViewFragment
                }
                delay(timeInterval)
            }
        }
    }
}