package com.misbah.quickcart.ui.listeners

import com.misbah.quickcart.core.data.model.Product

/**
 * @author: Mohammad Misbah
 * @since: 16-Dec-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
interface OnProductClickListener {
        fun onItemClick(product: Product)
        fun onItemDeleteClick(product: Product)
        fun onItemEditClick(product: Product)
        fun onAddToCartClick(product: Product)
}