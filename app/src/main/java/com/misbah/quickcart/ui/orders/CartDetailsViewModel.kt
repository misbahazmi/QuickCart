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


    private val addEditTaskEventChannel = Channel<AddEditTaskEvent>()
    val addEditTaskEvent = addEditTaskEventChannel.receiveAsFlow()

    fun onPlaceOrderClick() {
        if (order.value != null) {
           createOrder(order.value!!)
        }
    }

    fun showDatePicker(){
        showDateTimePicker()
    }

    fun onDateTimeResult(result: Long) {
        dateTimeWithResult(result)
    }

    private fun createOrder(order : Order) =  CoroutineScope(Dispatchers.IO).launch {
        quickCartDao.insertOrder(order)
        addEditTaskEventChannel.send(AddEditTaskEvent.NavigateBackWithResult(EDIT_TASK_RESULT_OK))
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