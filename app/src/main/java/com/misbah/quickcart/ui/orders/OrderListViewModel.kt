package com.misbah.quickcart.ui.orders

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.work.WorkManager
import com.misbah.quickcart.core.data.model.Order
import com.misbah.quickcart.core.data.model.Product
import com.misbah.quickcart.core.data.storage.PreferencesManager
import com.misbah.quickcart.core.data.storage.SortOrder
import com.misbah.quickcart.core.data.storage.QuickCartDao
import com.misbah.quickcart.ui.main.ADD_RESULT_OK
import com.misbah.quickcart.ui.main.EDIT_TASK_RESULT_OK
import com.nytimes.utils.AppLog
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
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
class OrderListViewModel
@Inject constructor(
    private val quickCartDao: QuickCartDao,
    private val preferencesManager: PreferencesManager,
    state: SavedStateHandle,
    private val context: Context
)  : ViewModel() {
    val searchQuery = state.getLiveData("searchQuery", "")

    val preferencesFlow = preferencesManager.preferencesFlow

    private val ordersEventChannel = Channel<OrdersEvent>()
    val tasksEvent = ordersEventChannel.receiveAsFlow()

    @OptIn(ExperimentalCoroutinesApi::class)
    private val tasksFlow = combine(
        searchQuery.asFlow(),
        preferencesFlow
    ) { query, filterPreferences ->
        Pair(query, filterPreferences)
    }.flatMapLatest { (query, filterPreferences) ->
        quickCartDao.getOrders(filterPreferences.taxIncluded)
    }
    val tasks = tasksFlow.asLiveData()

    var remainingTasks: LiveData<List<Product>>? = null

    fun onSortOrderSelected(sortOrder: SortOrder) = CoroutineScope(Dispatchers.IO).launch {
        preferencesManager.updateSortOrder(sortOrder)
    }

    fun onHideCompletedClick(taxtStatus: Boolean) = CoroutineScope(Dispatchers.IO).launch {
        preferencesManager.updateTextSatus(taxtStatus)
    }

    fun onFilterCategoryClick(taskCategory: Int) = CoroutineScope(Dispatchers.IO).launch {
        preferencesManager.updateProductCategory(taskCategory)
    }

    fun onTaskSelected(product: Order) = CoroutineScope(Dispatchers.IO).launch {
        ordersEventChannel.send(OrdersEvent.NavigateToEditTaskScreen(product))
    }

    fun onTaskCheckedChanged(product: Order, isChecked: Boolean) = CoroutineScope(Dispatchers.IO).launch {
        quickCartDao.updateOrder(product.copy(taxIncluded = isChecked))
    }

    fun onTaskSwiped(order: Order) {
        CoroutineScope(Dispatchers.IO).launch {
            quickCartDao.deleteOrder(order)
            ordersEventChannel.send(OrdersEvent.ShowUndoDeleteTaskMessage(order))
            try {
                WorkManager.getInstance(context).cancelAllWorkByTag(order.created.toString())
            } catch (e: Exception){
                e.localizedMessage?.let { AppLog.debugD(it) }
            }
        }
    }
    fun onUndoDeleteClick(order: Order) = CoroutineScope(Dispatchers.IO).launch {
        quickCartDao.insertOrder(order)
    }

    fun onAddNewTaskClick() = CoroutineScope(Dispatchers.IO).launch {
        ordersEventChannel.send(OrdersEvent.NavigateToAddTaskScreen)
    }

    fun onAddEditResult(result: Int) {
        when (result) {
            ADD_RESULT_OK -> showTaskSavedConfirmationMessage("Task added")
            EDIT_TASK_RESULT_OK -> showTaskSavedConfirmationMessage("Task updated")
        }
    }

    private fun showTaskSavedConfirmationMessage(text: String) = CoroutineScope(Dispatchers.IO).launch {
        ordersEventChannel.send(OrdersEvent.ShowTaskSavedConfirmationMessage(text))
    }

    fun onDeleteAllCompletedClick() = CoroutineScope(Dispatchers.IO).launch {
        ordersEventChannel.send(OrdersEvent.NavigateToDeleteAllCompletedScreen)
    }

    sealed class OrdersEvent {
        data object NavigateToAddTaskScreen : OrdersEvent()
        data class NavigateToEditTaskScreen(val order: Order) : OrdersEvent()
        data class ShowUndoDeleteTaskMessage(val order: Order) : OrdersEvent()
        data class ShowTaskSavedConfirmationMessage(val msg: String) : OrdersEvent()
        data object NavigateToDeleteAllCompletedScreen : OrdersEvent()
        data object QuitAppPopUp : OrdersEvent()
    }
}