package com.misbah.quickcart.ui.listeners

import com.misbah.quickcart.core.data.model.Order

/**
 * @author: Mohammad Misbah
 * @since: 16-Dec-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
interface OnItemClickListener {
        fun onItemClick(order: Order)
        fun onItemDeleteClick(order: Order)
        fun onItemViewClick(order: Order)
        fun onCheckBoxClick(order: Order, isChecked: Boolean)
    }