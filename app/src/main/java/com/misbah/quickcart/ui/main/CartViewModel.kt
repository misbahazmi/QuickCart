package com.misbah.quickcart.ui.main

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.misbah.quickcart.core.data.model.ShoppingCart

class CartViewModel : ViewModel() {
    val shoppingCart = MutableLiveData<ShoppingCart>(ShoppingCart())
}