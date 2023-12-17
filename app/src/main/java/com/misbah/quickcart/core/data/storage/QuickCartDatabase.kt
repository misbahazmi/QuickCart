package com.misbah.quickcart.core.data.storage

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import androidx.sqlite.db.SupportSQLiteDatabase
import com.misbah.quickcart.core.data.model.Converters
import com.misbah.quickcart.core.data.model.Order
import com.misbah.quickcart.core.data.model.Product
import com.misbah.quickcart.core.di.module.ApplicationScope
import com.nytimes.utils.AppEnums
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject
import javax.inject.Provider

@Database(entities = [Product::class, Order::class], version = 1, exportSchema = false)
@TypeConverters(Converters::class)
abstract class QuickCartDatabase : RoomDatabase() {

    abstract fun taskDao(): QuickCartDao

    class Callback @Inject constructor(
        private val database: Provider<QuickCartDatabase>,
        @ApplicationScope private val applicationScope: CoroutineScope
    ) : RoomDatabase.Callback() {

        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)

            val dao = database.get().taskDao()

            applicationScope.launch {
                //We can add here product or orders manually in the table
            }
        }
    }
}