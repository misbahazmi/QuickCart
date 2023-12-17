package com.misbah.quickcart.core.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson

/**
 * @author: Mohammad Misbah
 * @since: 15-Dec-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
class Converters {
    @TypeConverter
    fun listToJsonString(value: List<CartItem>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = Gson().fromJson(value, Array<CartItem>::class.java).toList()
}