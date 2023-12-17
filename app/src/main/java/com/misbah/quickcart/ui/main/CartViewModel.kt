package com.misbah.quickcart.ui.main

import android.annotation.SuppressLint
import android.content.Context
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.misbah.quickcart.core.data.model.CartItem
import com.misbah.quickcart.core.data.model.Order
import com.misbah.quickcart.core.data.model.ShoppingCart
import com.misbah.quickcart.core.data.storage.PreferenceUtils
import javax.inject.Inject

class CartViewModel : ViewModel() {
    val taxIncluded = MutableLiveData<Boolean>(true)
    private val taxRate = if(taxIncluded.value!!) 0.05 else 0.0
    val shoppingCart = MutableLiveData<ShoppingCart>(ShoppingCart(taxRate))
    val cartList = MutableLiveData<ArrayList<CartItem>>(arrayListOf())
}