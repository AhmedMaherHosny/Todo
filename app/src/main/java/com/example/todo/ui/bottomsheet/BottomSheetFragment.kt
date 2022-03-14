package com.example.todo.ui.bottomsheet

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.todo.data.db.ToDoDatabase
import com.example.todo.data.repositories.ToDoRepository
import com.example.todo.databinding.FragmentBottomSheetBinding
import com.example.todo.other.clearTime
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.michaldrabik.classicmaterialtimepicker.CmtpTimeDialogFragment
import com.michaldrabik.classicmaterialtimepicker.model.CmtpTime12
import com.michaldrabik.classicmaterialtimepicker.model.CmtpTime12.PmAm.AM
import com.michaldrabik.classicmaterialtimepicker.model.CmtpTime12.PmAm.PM
import com.michaldrabik.classicmaterialtimepicker.utilities.setOnTime12PickedListener
import java.time.LocalDate
import java.util.*
import kotlin.properties.Delegates

class BottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding: FragmentBottomSheetBinding
    private lateinit var dialog: CmtpTimeDialogFragment
    private lateinit var viewModel: BottomSheetViewModel
    private lateinit var calendarDate: Calendar
    private var currentHours by Delegates.notNull<Int>()
    private var currentMinutes by Delegates.notNull<Int>()
    private var comp by Delegates.notNull<CmtpTime12.PmAm>()
    private var time by Delegates.notNull<String>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetBinding.inflate(layoutInflater)
        val dataBase = ToDoDatabase(requireContext())
        val repository = ToDoRepository(dataBase)
        val factory = BottomSheetViewModelFactory(repository)
        viewModel = ViewModelProvider(this, factory)[BottomSheetViewModel::class.java]
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTime()
        calendarDate = Calendar.getInstance()
        binding.time.setOnClickListener {
            dialog.setOnTime12PickedListener {
                time = "${it.hour}:${it.minute}${it.pmAm}"
                binding.time.text = time
            }
            dialog.show(parentFragmentManager, "TimePicker")
        }
        binding.chooseDate.setOnClickListener {
            val datePicker = DatePickerDialog(
                requireContext(),
                { view, year, month, day ->
                    calendarDate.set(Calendar.DAY_OF_MONTH, day)
                    calendarDate.set(Calendar.MONTH, month)
                    calendarDate.set(Calendar.YEAR, year)
                    binding.chooseDate.text = ("" + day + "/" + (month + 1) + "/" + year)
                },
                calendarDate.get(Calendar.YEAR),
                calendarDate.get(Calendar.MONTH),
                calendarDate.get(Calendar.DAY_OF_MONTH)
            )
            datePicker.show()
        }
        binding.submitBtn.setOnClickListener {
            viewModel.addToDo(time, calendarDate.clearTime().time)
            viewModel.isAdded.observe(viewLifecycleOwner) {
                if (it == true)
                    Toast.makeText(requireContext(), "Added successfully", Toast.LENGTH_SHORT).show()
                    dismiss()
            }
        }
    }

    @SuppressLint("SetTextI18n")
    private fun initTime() {
        dialog = CmtpTimeDialogFragment.newInstance()
        val calendar = Calendar.getInstance()
        currentHours = calendar.get(Calendar.HOUR)
        currentMinutes = calendar.get(Calendar.MINUTE)
        val pmOrAm = calendar.get(Calendar.AM_PM)
        comp = if (pmOrAm == Calendar.AM)
            AM
        else
            PM
        if (currentHours == 0)
            currentHours = 12
        time = "$currentHours:$currentMinutes$comp"
        binding.time.text = time
        dialog.setInitialTime12(currentHours, currentMinutes, comp)
        binding.chooseDate.text = "" + calendar.get(Calendar.DAY_OF_MONTH) + "/" +
                (calendar.get(Calendar.MONTH) + 1) + "/" +
                calendar.get(Calendar.YEAR)
    }

}

