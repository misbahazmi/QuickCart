package com.misbah.quickcart.core.data.storage

import androidx.room.*
import com.misbah.quickcart.core.data.model.Order
import com.misbah.quickcart.core.data.model.Product
import kotlinx.coroutines.flow.Flow

@Dao
interface QuickCartDao {
    //Orders
    fun getOrders(sortOrder: SortOrder, taxIncluded: Boolean): Flow<List<Order>> {
       return when (sortOrder) {
           SortOrder.BY_DATE -> getOrders(taxIncluded)
           SortOrder.BY_ORDER_NO -> getOrders(taxIncluded)
       }
    }
    @Query("SELECT * FROM order_table WHERE (taxIncluded != :hideCompleted OR taxIncluded = 0)")
    fun getOrders( hideCompleted: Boolean): Flow<List<Order>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertOrder(order: Order)

    @Update
    fun updateOrder(order: Order)

    @Delete
    fun deleteOrder(order: Order)

    //Products
    fun getProducts(query: String, categoryId : Int): Flow<List<Product>> {
        return if (categoryId != 0 ){
            getProductsByCategory(query, categoryId)
        }else {
            getProducts(query)
        }
    }

    @Query("SELECT * FROM product_table")
    fun getProducts(): Flow<List<Product>>

    @Query("SELECT * FROM product_table WHERE name LIKE '%' || :searchQuery || '%'")
    fun getProducts(searchQuery: String): Flow<List<Product>>

    @Query("SELECT * FROM product_table WHERE category = :categoryId AND name LIKE '%' || :searchQuery || '%'")
    fun getProductsByCategory(searchQuery: String,  categoryId: Int): Flow<List<Product>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertProduct(product: Product) : Long

    @Update
    fun updateProduct(product: Product)

    @Delete
    fun deleteProduct(product: Product)

//    @Query("SELECT * FROM product_table WHERE (completed != :hideCompleted OR completed = 0) AND category = :categoryId AND name LIKE '%' || :searchQuery || '%' ORDER BY important DESC, name")
//    fun getTasksSortedByName(searchQuery: String, hideCompleted: Boolean,  categoryId: Int): Flow<List<Product>>
//
//    @Query("SELECT * FROM product_table WHERE (completed != :hideCompleted OR completed = 0) AND category = :categoryId AND name LIKE '%' || :searchQuery || '%' ORDER BY important DESC, important")
//    fun getTasksSortedByPriority(searchQuery: String, hideCompleted: Boolean,  categoryId: Int): Flow<List<Product>>
//
//    @Query("SELECT * FROM product_table WHERE (completed != :hideCompleted OR completed = 0) AND category = :categoryId AND name LIKE '%' || :searchQuery || '%' ORDER BY important DESC, due")
//    fun getTasksSortedByDateCreated(searchQuery: String, hideCompleted: Boolean,  categoryId: Int): Flow<List<Product>>
//
//    //All Category
//    @Query("SELECT * FROM product_table WHERE (completed != :hideCompleted OR completed = 0) AND name LIKE '%' || :searchQuery || '%' ORDER BY important DESC, name")
//    fun getTasksSortedByNameAllCategory(searchQuery: String, hideCompleted: Boolean): Flow<List<Product>>
//
//    @Query("SELECT * FROM product_table WHERE (completed != :hideCompleted OR completed = 0) AND name LIKE '%' || :searchQuery || '%' ORDER BY important DESC, important")
//    fun getTasksSortedByPriorityAllCategory(searchQuery: String, hideCompleted: Boolean): Flow<List<Product>>
//
//    @Query("SELECT * FROM product_table WHERE (completed != :hideCompleted OR completed = 0) AND name LIKE '%' || :searchQuery || '%' ORDER BY important DESC, due")
//    fun getTasksSortedByDateCreatedAllCategory(searchQuery: String, hideCompleted: Boolean): Flow<List<Product>>
//
//    @Query("SELECT * FROM product_table WHERE (completed != :hideCompleted OR completed = 0)  AND due > :current AND due < :future   ORDER BY important DESC, due")
//    fun getTasksRemainingTask(hideCompleted: Boolean, current : Long, future : Long): Flow<List<Product>>
//
//    @Insert(onConflict = OnConflictStrategy.REPLACE)
//    fun insert(product: Product)
//
//    @Update
//    fun update(product: Order)
//
//    @Delete
//    fun delete(product: Product)
//
//    @Query("DELETE FROM product_table WHERE completed = 1")
//    fun deleteCompletedTasks()
}