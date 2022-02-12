package com.example.todo.ui.bottomsheet

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.todo.databinding.FragmentBottomSheetBinding
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.michaldrabik.classicmaterialtimepicker.CmtpTimeDialogFragment
import com.michaldrabik.classicmaterialtimepicker.model.CmtpTime12
import com.michaldrabik.classicmaterialtimepicker.model.CmtpTime12.PmAm.PM
import com.michaldrabik.classicmaterialtimepicker.model.CmtpTime12.PmAm.AM
import com.michaldrabik.classicmaterialtimepicker.utilities.setOnTime12PickedListener
import java.util.*
import kotlin.properties.Delegates

class BottomSheetFragment : BottomSheetDialogFragment() {
    private lateinit var binding : FragmentBottomSheetBinding
    private lateinit var dialog : CmtpTimeDialogFragment
    private var currentHours by Delegates.notNull<Int>()
    private var currentMinutes by Delegates.notNull<Int>()
    private var comp by Delegates.notNull<CmtpTime12.PmAm>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBottomSheetBinding.inflate(layoutInflater)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initTime()
        binding.time.setOnClickListener{
            dialog.setOnTime12PickedListener{
                binding.time.text = "${it.hour}:${it.minute}${it.pmAm}"
            }
            dialog.show(parentFragmentManager, "TimePicker")
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
        binding.time.text = "$currentHours:$currentMinutes$comp"
        dialog.setInitialTime12(currentHours, currentMinutes, comp)
    }
}

