package com.misbah.quickcart.core.data.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nytimes.utils.AppEnums
import kotlinx.parcelize.Parcelize
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

/**
 * @author: Mohammad Misbah
 * @since: 15-Dec-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
@Entity(tableName = "product_table")
@Parcelize
data class Product(
    val name: String,
    val price: Double,
    val category: Int = AppEnums.ProductCategory.All.value,
    val created: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true) val id: Long = 0
) : Parcelable {
    fun getPriceFormatted(): String {
        return DecimalFormat( "AED #0.00" ,  DecimalFormatSymbols( Locale.ENGLISH)).format( price)
    }
}