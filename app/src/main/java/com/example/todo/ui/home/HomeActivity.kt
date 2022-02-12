package com.example.todo.ui.home

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction.TRANSIT_FRAGMENT_FADE
import com.example.todo.R
import com.example.todo.databinding.ActivityHomeBinding
import com.example.todo.ui.bottomsheet.BottomSheetFragment
import com.example.todo.ui.settingfragment.SettingFragment
import com.example.todo.ui.todofragment.ToDoFragment

class HomeActivity : AppCompatActivity() {
    private lateinit var binding : ActivityHomeBinding
    private lateinit var listFragment : Fragment
    private lateinit var settingFragment : Fragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        listFragment = ToDoFragment()
        settingFragment = SettingFragment()
        pushFragment(listFragment)
        binding.bottomNavigationView.background = null
        binding.bottomNavigationView.menu.getItem(1).isEnabled = false
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.navigation_list -> {
                    pushFragment(listFragment)
                    true
                }
                R.id.navigation_setting -> {
                    pushFragment(settingFragment)
                    true
                }
                else -> {false}
            }
        }
        binding.floatingAdd.setOnClickListener{
            val bottomSheet = BottomSheetFragment()
            bottomSheet.show(supportFragmentManager, "")
        }
    }

    private fun pushFragment(fragment:Fragment) {
        if (!fragment.isAdded) {
            supportFragmentManager.beginTransaction().apply {
                replace(R.id.wrapper, fragment)
                setTransition(TRANSIT_FRAGMENT_FADE)
                if (fragment == settingFragment)
                    addToBackStack(null)
                commit()
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        if (binding.bottomNavigationView.selectedItemId == R.id.navigation_list)
            finish()
        else
            binding.bottomNavigationView.selectedItemId = R.id.navigation_list
    }
}