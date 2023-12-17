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
//                dao.insert(Product("Wash the dishes","Wash the all the dishes"))
//                dao.insert(Product("Do the laundry", "Do the laundry and iron"))
//                dao.insert(Product("Buy groceries","Buy the groceries and shopping",4, important = AppEnums.TasksPriority.High.value))
//                dao.insert(Product("Groceries","Grocery shopping: buy milk, eggs, and vegetables.",4, important = AppEnums.TasksPriority.Medium.value))
//                dao.insert(Product("Prepare food", "Prepare food", important =  AppEnums.TasksPriority.Medium.value))
//                dao.insert(Product("Call mom", "Calling mom", category = 2, important = AppEnums.TasksPriority.High.value))
//                dao.insert(Product("Visit grandma","Visiting native place to meet grandma",  completed = true))
//                dao.insert(Product("Repair my bike","Repair and servicing of Bike"))
//                dao.insert(Product("Call Elon Musk","Calling Elon Musk", 1))
//                dao.insert(Product("Learning","Start learning a new language", 3, important = AppEnums.TasksPriority.Medium.value))
//                dao.insert(Product("New Course","Complete a certification course in a new skill", 3, important = AppEnums.TasksPriority.Low.value))
//                dao.insert(Product("New Hobby","Explore a new hobby, like painting or playing a musical instrument.", 3))
//                dao.insert(Product("Bill payment","Pay the electricity bill online.", 2,  important = AppEnums.TasksPriority.High.value))
//                dao.insert(Product("Report & Meeting","Complete the report for the quarterly meeting.", 1,important = AppEnums.TasksPriority.High.value))
//                dao.insert(Product("Project Status","Send out the project status update email to the team.", 1,important = AppEnums.TasksPriority.Medium.value))
            }
        }
    }
}