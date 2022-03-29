package com.example.todo.ui.todofragment

import android.annotation.SuppressLint
import android.graphics.Canvas
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.todo.R
import com.example.todo.data.db.ToDoDatabase
import com.example.todo.data.db.entities.ToDoItem
import com.example.todo.data.repositories.ToDoRepository
import com.example.todo.databinding.FragmentToDoBinding
import com.example.todo.other.ToDoListAdapter
import com.example.todo.other.clearTime
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import com.prolificinteractive.materialcalendarview.CalendarDay
import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*


class ToDoFragment : Fragment() {
    private lateinit var binding: FragmentToDoBinding
    lateinit var viewModel: ToDoFragmentViewModel
    private lateinit var navController: NavController
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

    @SuppressLint("NotifyDataSetChanged", "UseCompatLoadingForDrawables")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)
        (activity as AppCompatActivity?)!!.supportActionBar?.apply {
            setDisplayHomeAsUpEnabled(false)
            title = "To Do List"
        }
        initView()
        clickItem()
        swipeToDelete()
    }

    private fun swipeToDelete() {
        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder
            ): Boolean {
                return false
            }

            @SuppressLint("ResourceType")
            override fun onChildDraw(
                c: Canvas,
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                dX: Float,
                dY: Float,
                actionState: Int,
                isCurrentlyActive: Boolean
            ) {
                RecyclerViewSwipeDecorator.Builder(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )
                    .addSwipeRightBackgroundColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.delete
                        )
                    )
                    .addCornerRadius(0, 15)
                    .addSwipeRightActionIcon(R.drawable.ic_trash)
                    .addSwipeRightLabel("Delete")
                    .setSwipeRightLabelColor(
                        ContextCompat.getColor(
                            requireContext(),
                            R.color.white
                        )
                    )
                    .setSwipeRightLabelTypeface(Typeface.DEFAULT_BOLD)
                    .create()
                    .decorate()
                super.onChildDraw(
                    c,
                    recyclerView,
                    viewHolder,
                    dX,
                    dY,
                    actionState,
                    isCurrentlyActive
                )

            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val deletedItem = adapter.TodoList?.get(viewHolder.bindingAdapterPosition)
                val position = viewHolder.bindingAdapterPosition
                adapter.TodoList?.removeAt(position)
                adapter.notifyItemRemoved(position)
                Snackbar.make(
                    binding.todoRecycler,
                    "${deletedItem!!.label} removed.",
                    Snackbar.LENGTH_LONG
                )
                    .setAction("Undo") {
                        adapter.TodoList?.add(position, deletedItem)
                        adapter.notifyItemInserted(position)
                    }.addCallback(object : BaseTransientBottomBar.BaseCallback<Snackbar>() {
                        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
                            super.onDismissed(transientBottomBar, event)
                            if (event == Snackbar.Callback.DISMISS_EVENT_TIMEOUT) {
                                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                                    viewModel.delete(deletedItem)
                                }
                                Toast.makeText(
                                    requireContext(),
                                    "Item deleted successfully.",
                                    Toast.LENGTH_SHORT
                                ).show()
                            }
                        }
                    }).show()
            }
        }).attachToRecyclerView(binding.todoRecycler)
    }

    private fun clickItem() {
        adapter.onItemClickAdapter = object : ToDoListAdapter.OnItemClickAdapter {
            override fun onItemClick(position: Int, item: ToDoItem) {
                val action = ToDoFragmentDirections.actionToDoFragmentToToDoViewFragment(
                    item.time,
                    item.label,
                    item.description,
                    item.date,
                    item.isDone,
                    item.id,
                )
                navController.navigate(action)
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onBtnClick(position: Int, item: ToDoItem) {
                viewLifecycleOwner.lifecycleScope.launch(Dispatchers.IO) {
                    viewModel.markDone(item.id, true)
                }
            }
        }
    }


    @SuppressLint("ResourceType")
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

    }

    @SuppressLint("NotifyDataSetChanged")
    private fun getToDoFromDB() {
        viewModel.getToDoByDate(calendar.clearTime().time).observe(viewLifecycleOwner) {
            binding.wrapper.isVisible = it.isEmpty()
            adapter.TodoList = it.toMutableList()
            adapter.notifyDataSetChanged()
        }
    }

}