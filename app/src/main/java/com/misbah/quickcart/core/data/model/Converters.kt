package com.misbah.quickcart.core.data.model

import androidx.room.TypeConverter
import com.google.gson.Gson

class Converters {
    @TypeConverter
    fun listToJsonString(value: List<CartItem>?): String = Gson().toJson(value)

    @TypeConverter
    fun jsonStringToList(value: String) = Gson().fromJson(value, Array<CartItem>::class.java).toList()
}