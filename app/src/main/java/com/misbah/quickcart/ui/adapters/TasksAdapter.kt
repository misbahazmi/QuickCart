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
import com.misbah.quickcart.databinding.ItemTaskBinding
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
class TasksAdapter(private val listener: OnItemClickListener) :
    ListAdapter<Order, TasksAdapter.TasksViewHolder>(DiffCallback()) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TasksViewHolder {
        val binding = ItemTaskBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return TasksViewHolder(binding)
    }

    override fun onBindViewHolder(holder: TasksViewHolder, position: Int) {
        val currentItem = getItem(position)
        holder.bind(currentItem)
    }

    inner class TasksViewHolder(private val binding: ItemTaskBinding) :
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
                checkBoxCompleted.setOnClickListener {
                    val position = adapterPosition
                    if (position != RecyclerView.NO_POSITION) {
                        val task = getItem(position)
                        listener.onCheckBoxClick(task, checkBoxCompleted.isChecked)
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

        fun bind(order: Order) {
            binding.apply {
                checkBoxCompleted.isChecked = order.taxIncluded
                textViewName.text = order.carts
                textViewTitle.text = order.amount.toString()
                textViewDateDue.text = order.createdDateFormatted
                textPriority.text = order.displayPriority
                textPriority.isVisible = order.status != 0
                textViewName.paint.isStrikeThruText = order.taxIncluded
                textViewTitle.paint.isStrikeThruText = order.taxIncluded
                textViewDateDue.paint.isStrikeThruText = order.taxIncluded
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
        popup.menuInflater.inflate(R.menu.navigation_menu_product_items, popup.menu)
        popup.setOnMenuItemClickListener { item ->
            when(item.itemId){
                R.id.nav_delete->{
                    listener.onItemDeleteClick(product)
                }
                R.id.nav_edit->{
                    listener.onItemEditClick(product)
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