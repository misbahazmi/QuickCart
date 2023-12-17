package com.misbah.quickcart.core.data.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

/**
 * @author: Mohammad Misbah
 * @since: 16-Dec-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
@Parcelize
data class CartItem(
    val productId: Long,
    val name: String,
    val quantity: Int,
    val price: Double,
    val totalPrice: Double,
    val created: Long = System.currentTimeMillis()
) : Parcelable