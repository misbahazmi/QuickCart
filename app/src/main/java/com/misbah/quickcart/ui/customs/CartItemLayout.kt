package com.misbah.quickcart.ui.customs

import android.annotation.SuppressLint
import android.content.Context
import android.util.AttributeSet
import android.widget.LinearLayout
import android.widget.TextView
import com.misbah.quickcart.R
import com.misbah.quickcart.core.data.model.Product

/**
 * @author: Mohammad Misbah
 * @since: 17-Dec-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
class CartItemLayout : LinearLayout {
    private var name: TextView? = null
    private var quantity: TextView? = null
    private var total: TextView? = null

    constructor(context: Context?) : super(context) {
        init()
    }

    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs) {
        init()
    }

    private fun init() {
        inflate(context, R.layout.item_cart, this)
        name = findViewById(R.id.text_product_name)
        total = findViewById(R.id.text_total_price)
        quantity = findViewById(R.id.text_quantity_price)
    }

    @SuppressLint("SetTextI18n")
    fun setData(product : Product, qnty: Int) {
        name?.text = product.name
        val totalPrice = product.price * qnty
        total?.text = product.getPriceFormatted(totalPrice)
        quantity?.text = "${qnty} X ${product.getPriceFormatted(product.price)}"
    }

}