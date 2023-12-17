package com.misbah.quickcart.ui.orders

import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.misbah.quickcart.core.data.model.Order
import com.misbah.quickcart.core.data.model.Product
import com.misbah.quickcart.core.data.storage.QuickCartDao
import com.misbah.quickcart.ui.main.ADD_RESULT_OK
import com.misbah.quickcart.ui.main.EDIT_TASK_RESULT_OK
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author: Mohammad Misbah
 * @since: 16-DEC-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
class CartDetailsViewModel @Inject constructor(
    private val quickCartDao: QuickCartDao,
    private val context : Context
) : ViewModel() {

    var order = MutableLiveData<Order>()

    var selectedDateTime = order.value?.created ?: System.currentTimeMillis()
    var taskTitle = order.value?.carts ?: ""
    var taskDescription = order.value?.carts ?: ""
    var taskImportance = order.value?.status ?: 0
    var tasksCategory = order.value?.status ?: 0

    var dueDate = selectedDateTime


    private val addEditTaskEventChannel = Channel<AddEditTaskEvent>()
    val addEditTaskEvent = addEditTaskEventChannel.receiveAsFlow()

    fun onSaveClick() {
        if (taskTitle.isBlank()) {
            showInvalidInputMessage("Title cannot be empty")
            return
        }
        if (taskDescription.isBlank()) {
            showInvalidInputMessage("Description cannot be empty")
            return
        }
        if (order.value != null) {
            //val updatedTask =  order.value!!.copy(name = taskDescription, title = taskTitle , important = taskImportance, category = tasksCategory, due = dueDate)
            //updateTask(updatedTask)
        } else {
            //val newProduct = Product(name = taskDescription, title = taskTitle, important = taskImportance, category = tasksCategory, due = dueDate)
           // createTask(newProduct)
        }
    }


    fun showDatePicker(){
        showDateTimePicker()
    }

    fun onDateTimeResult(result: Long) {
        dateTimeWithResult(result)
    }

    private fun createTask(product: Product) =  CoroutineScope(Dispatchers.IO).launch {
        //quickCartDao.insert(product)
        addEditTaskEventChannel.send(AddEditTaskEvent.NavigateBackWithResult(ADD_RESULT_OK))
    }

    private fun updateTask(product: Product) =  CoroutineScope(Dispatchers.IO).launch {
        //quickCartDao.update(product)
        addEditTaskEventChannel.send(AddEditTaskEvent.NavigateBackWithResult(EDIT_TASK_RESULT_OK))
    }

    private fun showInvalidInputMessage(text: String) = viewModelScope.launch {
        addEditTaskEventChannel.send(AddEditTaskEvent.ShowInvalidInputMessage(text))
    }

    private fun dateTimeWithResult(text: Long) = viewModelScope.launch {
        addEditTaskEventChannel.send(AddEditTaskEvent.DateTimeWithResult(text))
    }

    private fun showDateTimePicker() = viewModelScope.launch {
        addEditTaskEventChannel.send(AddEditTaskEvent.ShowDateTimePicker)
    }

    sealed class AddEditTaskEvent {
        data class ShowInvalidInputMessage(val msg: String) : AddEditTaskEvent()
        data class NavigateBackWithResult(val result: Int) : AddEditTaskEvent()
        data object ShowDateTimePicker : AddEditTaskEvent()
        data class DateTimeWithResult(val result: Long) : AddEditTaskEvent()
    }
}