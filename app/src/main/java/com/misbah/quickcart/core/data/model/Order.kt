package com.misbah.quickcart.core.data.model


import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.nytimes.utils.AppEnums
import kotlinx.parcelize.Parcelize
import java.text.DateFormat

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
    val carts: String,
    val amount: Double,
    val discountAmount:  Double,
    val taxAmount:  Double,
    val status:  Int = AppEnums.TasksPriority.Normal.value,
    val taxIncluded: Boolean = false,
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
}