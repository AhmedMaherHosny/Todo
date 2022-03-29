package com.example.todo.ui.todoviewfragment

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.navArgs
import com.example.todo.R
import com.example.todo.data.db.ToDoDatabase
import com.example.todo.data.repositories.ToDoRepository
import com.example.todo.databinding.FragmentToDoViewBinding
import com.example.todo.other.clearTime
import com.michaldrabik.classicmaterialtimepicker.CmtpTimeDialogFragment
import com.michaldrabik.classicmaterialtimepicker.model.CmtpTime12
import com.michaldrabik.classicmaterialtimepicker.utilities.setOnTime12PickedListener
import java.util.*


class ToDoViewFragment : Fragment() {
    private lateinit var binding: FragmentToDoViewBinding
    private lateinit var navController : NavController
    private lateinit var viewModel : ToDoViewFragmentViewModel
    private val args: ToDoViewFragmentArgs by navArgs()
    private lateinit var calendar: Calendar
    private lateinit var time : String
    private lateinit var calendarDate: Calendar
    private lateinit var dialog: CmtpTimeDialogFragment


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentToDoViewBinding.inflate(layoutInflater)
        val dataBase = ToDoDatabase(requireContext())
        val repository = ToDoRepository(dataBase)
        val factory = ToDoViewFragmentViewModelFactory(repository, args)
        viewModel = ViewModelProvider(this, factory)[ToDoViewFragmentViewModel::class.java]
        binding.lifecycleOwner = this
        binding.vm = viewModel
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        time = args.time
        navController = Navigation.findNavController(view)
        calendarDate = Calendar.getInstance()
        calendarDate.time = args.date
        dialog = CmtpTimeDialogFragment.newInstance()
        dialog.setInitialTime12(1,1,CmtpTime12.PmAm.AM)
        (activity as AppCompatActivity?)!!.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(true)
//            setHomeButtonEnabled(true)
            title = "To Do Edit"
//            NavUtils.navigateUpFromSameTask(requireActivity())
        }
        calendar = Calendar.getInstance()
        calendar.time = args.date
        binding.time.text = time
        binding.chooseDate.text =
            "${calendar.get(Calendar.DAY_OF_MONTH)}/${calendar.get(Calendar.MONTH) + 1}/${
                calendar.get(Calendar.YEAR)
            }"


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
            viewModel.updateToDo(time, calendarDate.clearTime().time)
            viewModel.isAdded.observe(viewLifecycleOwner) {
                if (it == true)
                    Toast.makeText(requireContext(), "Updated successfully", Toast.LENGTH_SHORT).show()
                    navController.navigate(R.id.action_toDoViewFragment_to_toDoFragment2)
            }
        }

    }

}