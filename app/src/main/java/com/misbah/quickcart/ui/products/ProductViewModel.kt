package com.misbah.quickcart.ui.products

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asFlow
import androidx.lifecycle.asLiveData
import androidx.work.WorkManager
import com.misbah.quickcart.core.data.model.Category
import com.misbah.quickcart.core.data.model.Order
import com.misbah.quickcart.core.data.model.Product
import com.misbah.quickcart.core.data.storage.PreferencesManager
import com.misbah.quickcart.core.data.storage.QuickCartDao
import com.misbah.quickcart.ui.main.ADD_RESULT_OK
import com.misbah.quickcart.ui.orders.OrderListViewModel
import com.nytimes.utils.AppEnums
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
 * @since: 16-Dec-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
class ProductViewModel @Inject constructor(
    private val quickCartDao: QuickCartDao,
    private val preferencesManager: PreferencesManager,
    state: SavedStateHandle,
) : ViewModel() {

    private val productEventChannel = Channel<ProductEvent>()
    val productEvent = productEventChannel.receiveAsFlow()
    val searchQuery = state.getLiveData("searchQuery", "")

    val preferencesFlow = preferencesManager.preferencesFlow
    fun onFilterCategoryClick(taskCategory: Int) = CoroutineScope(Dispatchers.IO).launch {
        preferencesManager.updateProductCategory(taskCategory)
    }
    fun categoryList(): LiveData<ArrayList<Category>> {
        val catList = arrayListOf<Category>()
        for ((index, data) in AppEnums.ProductCategory.values().distinct().withIndex()) {
            catList.add(Category(data.value, data.name))
        }
        catList.removeAt(0)
        return MutableLiveData<ArrayList<Category>>().apply { value = catList }
    }

    fun onTaskSwiped(product: Product) {
        CoroutineScope(Dispatchers.IO).launch {
            quickCartDao.deleteProduct(product)
            productEventChannel.send(ProductEvent.ShowUndoDeleteTaskMessage(product))
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    private val productsFlow = combine(
        searchQuery.asFlow(),
        preferencesFlow
    ) { query, filterPreferences ->
        Pair(query, filterPreferences)
    }.flatMapLatest { (query, filterPreferences) ->
        quickCartDao.getProducts()
    }
    val products = productsFlow.asLiveData()
    var product = MutableLiveData<Product>()

    var productTitle = product.value?.name ?: ""
    var productPrice = product.value?.price ?: 0.00

    fun onSaveClick() {
        if (productTitle.isBlank()) {
            displayMessage("Name cannot be empty")
            return
        }
        if (productPrice <= 0) {
            displayMessage("Price cannot be zero or less")
            return
        }
        val newProduct = Product(name = productTitle, price = productPrice)
        createTask(newProduct)
    }

    private fun createTask(product: Product) =  CoroutineScope(Dispatchers.IO).launch {
        val id = quickCartDao.insertProduct(product)
        productEventChannel.send(ProductEvent.NavigateBackWithResult(id.toInt()))
    }

    fun deleteProduct(product: Product){
        CoroutineScope(Dispatchers.IO).launch {
            quickCartDao.deleteProduct(product)
            productEventChannel.send(ProductEvent.ShowUndoDeleteTaskMessage(product))
        }
    }

    fun addProductToCart(product: Product){

    }

    fun onUndoDeleteClick(product: Product) = CoroutineScope(Dispatchers.IO).launch {
        quickCartDao.insertProduct(product)
    }


    fun displayMessage(text: String) = CoroutineScope(Dispatchers.IO).launch {
       showMessage(text)
    }

    private fun showMessage(text: String) = CoroutineScope(Dispatchers.IO).launch {
        productEventChannel.send(ProductEvent.ShowManageProductMessage(text))
    }

    sealed class ProductEvent {
        data object NavigateToAddProductDialog : ProductEvent()
        data class NavigateBackWithResult(val result: Int) : ProductEvent()
        data class ShowManageProductMessage(val msg: String) : ProductEvent()
        data class ShowUndoDeleteTaskMessage(val product: Product) : ProductEvent()
    }
}