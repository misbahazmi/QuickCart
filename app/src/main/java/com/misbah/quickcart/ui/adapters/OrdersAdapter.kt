package com.misbah.quickcart.ui.adapters

import android.annotation.SuppressLint
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.view.menu.MenuBuilder
import androidx.appcompat.view.menu.MenuPopupHelper
import androidx.appcompat.widget.PopupMenu
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.misbah.quickcart.R
import com.misbah.quickcart.core.data.model.Order
import com.misbah.quickcart.databinding.ItemOrdersBinding
import com.misbah.quickcart.ui.listeners.OnItemClickListener
import com.nytimes.utils.AppEnums

/**
 * @author: Mohammad Misbah
 * @since: 03-Oct-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
class OrdersAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Order, OrdersAdapter.OrdersViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrdersViewHolder {
        val binding = ItemOrdersBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return OrdersViewHolder(binding)
    }

    override fun onBindViewHolder(holder: OrdersViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class OrdersViewHolder(private val binding: ItemOrdersBinding) :
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

        @SuppressLint("SetTextI18n")
        fun bind(order: Order) {
            binding.apply {
                textViewName.text = "Order #${order.id}"
                textViewTitle.text = order.getAmountFormatted(order.totalAmout)
                textViewDate.text = order.createdDateFormatted
                textPriority.text = order.displayPriority
                textPriority.isVisible = order.status != 0
                when(order.status){
                    AppEnums.TasksPriority.High.value->{
                        textPriority.background = ContextCompat.getDrawable(root.context, R.drawable.bg_hight_priority)
                        textPriority.setTextColor( ContextCompat.getColor(root.context, R.color.text_color_high))
                    }

                    AppEnums.TasksPriority.Medium.value->{
                        textPriority.background = ContextCompat.getDrawable(root.context, R.drawable.bg_medium_priority)
                        textPriority.setTextColor( ContextCompat.getColor(root.context, R.color.text_color_medium))
                    }

                    AppEnums.TasksPriority.Low.value->{
                        textPriority.background = ContextCompat.getDrawable(root.context, R.drawable.bg_low_priority)
                        textPriority.setTextColor( ContextCompat.getColor(root.context, R.color.text_color_low))
                    }
                }
            }
        }
    }

    class DiffCallback : DiffUtil.ItemCallback<Order>() {
        override fun areItemsTheSame(oldItem: Order, newItem: Order) =
            oldItem.id == newItem.id

        override fun areContentsTheSame(oldItem: Order, newItem: Order) =
            oldItem == newItem
    }

    @SuppressLint("RestrictedApi")
    fun openOptionMenu(v: View, product: Order, listener : OnItemClickListener) {
        val popup = PopupMenu(v.context, v)
        popup.menuInflater.inflate(R.menu.navigation_menu_order_items, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.nav_delete->{
                    listener.onItemDeleteClick(product)
                }
                R.id.nav_view_order->{
                    listener.onItemViewClick(product)
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