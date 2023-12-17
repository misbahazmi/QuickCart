package com.misbah.quickcart.core.data.storage

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.misbah.quickcart.core.data.model.ShoppingCart
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject

private const val TAG = "PreferencesManager"

enum class SortOrder {BY_ORDER_NO, BY_DATE}

data class FilterPreferences(val sortOrder: SortOrder, val taxIncluded: Boolean, val category : Int)

class PreferencesManager @Inject constructor(private  val context : Context) {

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

    val preferencesFlow = context.dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                Log.e(TAG, "Error reading preferences", exception)
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val sortOrder = SortOrder.valueOf(
                preferences[PreferencesKeys.SORT_ORDER] ?: SortOrder.BY_DATE.name
            )
            val taxIncluded = preferences[PreferencesKeys.TAX_INCLUDED] ?: false
            val productCategory = preferences[PreferencesKeys.PRODUCT_CATEGORY] ?: 0
            val cartData = preferences[PreferencesKeys.CART_DATA] ?: ShoppingCart()
            FilterPreferences(sortOrder, taxIncluded, productCategory)
        }
    suspend fun updateCartData(cartData: String) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.CART_DATA] = cartData
        }
    }
    suspend fun updateSortOrder(sortOrder: SortOrder) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.SORT_ORDER] = sortOrder.name
        }
    }

    suspend fun updateTextSatus(taxIncluded: Boolean) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.TAX_INCLUDED] = taxIncluded
        }
    }

    suspend fun updateProductCategory(category: Int) {
        context.dataStore.edit { preferences ->
            preferences[PreferencesKeys.PRODUCT_CATEGORY] = category
        }
    }

    private object PreferencesKeys {
        val CART_DATA = stringPreferencesKey("cart_data")
        val SORT_ORDER = stringPreferencesKey("sort_order")
        val TAX_INCLUDED = booleanPreferencesKey("tax_included")
        val PRODUCT_CATEGORY = intPreferencesKey("product_category")
    }
}