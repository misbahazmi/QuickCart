package com.misbah.quickcart.ui.adapters

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.appcompat.widget.PopupMenu
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.misbah.quickcart.R
import com.misbah.quickcart.core.data.model.Product
import com.misbah.quickcart.databinding.ItemCategoryBinding
import com.misbah.quickcart.ui.listeners.OnProductClickListener

/**
 * @author: Mohammad Misbah
 * @since: 16-DEC-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
class ProductAdapter(private val listener: OnProductClickListener) :
    ListAdapter<Product, ProductAdapter.CategoryViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CategoryViewHolder {
        val binding = ItemCategoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return CategoryViewHolder(binding)
    }

    override fun onBindViewHolder(holder: CategoryViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class CategoryViewHolder(private val binding: ItemCategoryBinding) :
        RecyclerView.ViewHolder(binding.root) {

        init {
            binding.apply {
                root.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val task = getItem(position)
                        listener.onItemClick(task)
                    }
                }
                labelOption.setOnClickListener{
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val task = getItem(position)
                        openOptionMenu(root, task, listener)
                    }
                }
            }
        }

        fun bind(task: Product) {
            binding.apply {
                textViewTitle.text = task.name
                textViewPrice.text = task.getPriceFormatted()
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Product>() {
        override fun areItemsTheSame(oldItem: Product, newItem: Product) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Product, newItem: Product) =
            oldItem == newItem
    }

    @SuppressLint("RestrictedApi")
    fun openOptionMenu(v: View, product: Product, listener : OnProductClickListener) {
        val popup = PopupMenu(v.context, v)
        popup.menuInflater.inflate(R.menu.navigation_menu_product_items, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.nav_delete->{
                    listener.onItemDeleteClick(product)
                }
                R.id.nav_add_cart->{
                    listener.onAddToCartClick(product)
                }
            }
            true
        }
        val menuHelper = MenuPopupHelper(v.context, (popup.menu as MenuBuilder), v)
        menuHelper.setForceShowIcon(true)
        menuHelper.gravity = Gravity.END
        menuHelper.show()
    }
}