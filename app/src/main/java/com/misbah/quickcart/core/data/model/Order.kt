package com.misbah.quickcart.core.data.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.Gson
import com.nytimes.utils.AppEnums
import kotlinx.parcelize.Parcelize
import java.text.DateFormat
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
@Entity(tableName = "order_table")
@Parcelize
data class Order(
    var carts: String,
    var amount: Double,
    var taxAmount: Double,
    var totalAmout: Double,
    var status: Int = AppEnums.TasksPriority.Normal.value,
    var taxIncluded: Boolean = false,
    val created: Long = System.currentTimeMillis(),
    @PrimaryKey(autoGenerate = true) val id: Long = 0
) : Parcelable {
    val createdDateFormatted: String
    get() = DateFormat.getDateTimeInstance().format(created)
    val displayPriority: String
    get() = when(status){
        AppEnums.TasksPriority.Normal.value->
            AppEnums.TasksPriority.Normal.name
        AppEnums.TasksPriority.Low.value->
            AppEnums.TasksPriority.Low.name
        AppEnums.TasksPriority.Medium.value->
            AppEnums.TasksPriority.Medium.name
        AppEnums.TasksPriority.High.value->
            AppEnums.TasksPriority.High.name
        else->
            AppEnums.TasksPriority.Normal.name

    }

    fun getAmountFormatted(amount  : Double): String {
        return DecimalFormat( "AED #0.00" ,  DecimalFormatSymbols( Locale.ENGLISH)).format( amount)
    }

    fun getCartList() : ArrayList<CartItem>{
        return Gson().fromJson(carts, Array<CartItem>::class.java).toList() as ArrayList<CartItem>
    }

    fun toCartLisString(list : ArrayList<CartItem>) : String{
        return Gson().toJson(list)
    }

}