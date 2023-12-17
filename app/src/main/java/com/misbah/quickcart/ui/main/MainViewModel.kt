package com.misbah.quickcart.ui.main


import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.misbah.quickcart.core.data.model.ShoppingCart
import com.misbah.quickcart.ui.category.CategoryViewModel
import com.misbah.quickcart.ui.products.ProductViewModel
import com.misbah.quickcart.ui.orders.OrderListViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author: MOHAMMAD MISBAH
 * @since: 16-Jul-2022
 * @sample: Technology Assessment for Android Role
 * @desc MainView Model
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java||Flutter
 */
class MainViewModel @Inject constructor() : ViewModel(){
    private val ordersEventChannel = Channel<OrderListViewModel.OrdersEvent>()
    private val categoryEventChannel = Channel<CategoryViewModel.CategoryEvent>()
    private val productEventChannel = Channel<ProductViewModel.ProductEvent>()
    val tasksEvent = ordersEventChannel.receiveAsFlow()
    val categoryEvent = categoryEventChannel.receiveAsFlow()
    val productEvent = productEventChannel.receiveAsFlow()

    fun onAddNewTaskClick() = CoroutineScope(Dispatchers.IO).launch {
        ordersEventChannel.send(OrderListViewModel.OrdersEvent.NavigateToAddTaskScreen)
    }

    fun onAddNewCategoryClick() = CoroutineScope(Dispatchers.IO).launch {
        categoryEventChannel.send(CategoryViewModel.CategoryEvent.NavigateToAddCategoryDialog)
    }

    fun onAddNewProductClick() = CoroutineScope(Dispatchers.IO).launch {
        productEventChannel.send(ProductViewModel.ProductEvent.NavigateToAddProductDialog)
    }

    fun onBackClickQuitApp() = CoroutineScope(Dispatchers.IO).launch {
        ordersEventChannel.send(OrderListViewModel.OrdersEvent.QuitAppPopUp)
    }
}