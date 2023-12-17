package com.misbah.quickcart.ui.orders

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.misbah.chips.ChipListener
import com.misbah.quickcart.R
import com.misbah.quickcart.core.base.BaseFragment
import com.misbah.quickcart.databinding.FragmentCartDetailsBinding
import com.misbah.quickcart.ui.dialogs.TimePickerFragment
import com.misbah.quickcart.ui.listeners.OnDateTimeListener
import com.misbah.quickcart.ui.main.CartViewModel
import com.misbah.quickcart.ui.main.MainActivity
import com.misbah.quickcart.ui.utils.exhaustive
import com.nytimes.utils.AppEnums
import kotlinx.coroutines.launch
import java.text.DateFormat
import javax.inject.Inject

/**
 * @author: Mohammad Misbah
 * @since: 16-DEC-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
class CartDetailsFragment : BaseFragment<CartDetailsViewModel>(), OnDateTimeListener {
    private val tasksArgs: CartDetailsFragmentArgs by navArgs()
    private var _binding: FragmentCartDetailsBinding? = null
    internal lateinit var viewModel: CartDetailsViewModel
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val binding get() = _binding!!
    private val sharedViewModel: CartViewModel by activityViewModels()
    override fun getViewModel(): CartDetailsViewModel {
        viewModel = ViewModelProvider(viewModelStore, factory)[CartDetailsViewModel::class.java]
        return viewModel
    }
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartDetailsBinding.inflate(inflater, container, false)
        viewModel.order.value = tasksArgs.order
        binding.farg = this@CartDetailsFragment
        binding.cartViewModel = sharedViewModel
        return binding.root
    }
    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            if(viewModel.order.value == null){
                btnSaveUpdate.text = getString(R.string.save)
                textHeading.text = getString(R.string.add_tasks)
                imgHeader.setImageResource(R.drawable.ic_add_task_data)
            }
            else {
                btnSaveUpdate.text = getString(R.string.update)
                textHeading.text = getString(R.string.update_tasks)
                imgHeader.setImageResource(R.drawable.ic_edit_task_data)
            }

            editTextTaskTitle.setText(viewModel.order.value?.carts ?: "")
            editTextTaskDescription.setText(viewModel.order.value?.carts ?: "")
            textViewDateDue.text = "Due Date: ${viewModel.order.value?.createdDateFormatted ?: DateFormat.getDateTimeInstance().format(viewModel.dueDate)}"
            viewModel.taskTitle = viewModel.order.value?.carts ?: ""
            viewModel.taskDescription = viewModel.order.value?.carts ?: ""
            viewModel.dueDate = viewModel.order.value?.created ?: System.currentTimeMillis()

            editTextTaskTitle.addTextChangedListener {
                viewModel.taskTitle = it.toString()
            }
            editTextTaskDescription.addTextChangedListener {
                viewModel.taskDescription = it.toString()
            }
            chipTasksPriority.setChipListener( object : ChipListener {
                override fun chipSelected(index: Int) {
                    viewModel.taskImportance =  AppEnums.TasksPriority.values().distinct()
                        .withIndex().first { it.value.value == index }.value.value
                }
                override fun chipDeselected(index: Int) {}
                override fun chipRemoved(index: Int) {}
              }
            )
            for ((index,data)  in AppEnums.TasksPriority.values().distinct().withIndex()){
               chipTasksPriority.addChip(data.name)
            }
            if(viewModel.order.value?.status != null && viewModel.order.value?.status != 0)
                viewModel.order.value?.status?.let { chipTasksPriority.setSelectedChip(it) }
            else
                chipTasksPriority.setSelectedChip(0)
        }

        @Suppress("IMPLICIT_CAST_TO_ANY")
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addEditTaskEvent.collect { event ->
                    when (event) {
                        is CartDetailsViewModel.AddEditTaskEvent.ShowInvalidInputMessage -> {
                            Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                        }
                        is CartDetailsViewModel.AddEditTaskEvent.NavigateBackWithResult -> {
                            binding.editTextTaskTitle.clearFocus()
                            binding.editTextTaskDescription.clearFocus()
                            setFragmentResult(
                                "add_edit_request",
                                bundleOf("add_edit_result" to event.result)
                            )
                            findNavController().popBackStack()
                        }
                        is CartDetailsViewModel.AddEditTaskEvent.ShowDateTimePicker ->{
                            TimePickerFragment.newInstance(this@CartDetailsFragment).show(childFragmentManager, "timePicker")
                        }
                        is CartDetailsViewModel.AddEditTaskEvent.DateTimeWithResult ->{
                            viewModel.selectedDateTime = event.result
                            viewModel.dueDate = event.result
                            binding.textViewDateDue.text = "Due Date: ${DateFormat.getDateTimeInstance().format(viewModel.dueDate)}"
                        }
                        else -> {}
                    }.exhaustive
                }
            }
        }
        (requireActivity() as MainActivity).hideFAB()
    }

    fun clickOnSave() {
        viewModel.onSaveClick()
    }

    fun clickOnDateTime(){
       viewModel.showDatePicker()
    }

    override fun onDateTimeSelected(timestamp: Long) {
        viewModel.onDateTimeResult(timestamp)
    }
}