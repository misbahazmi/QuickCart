package com.misbah.quickcart.ui.orders

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.setFragmentResult
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.material.snackbar.Snackbar
import com.misbah.quickcart.core.base.BaseFragment
import com.misbah.quickcart.core.data.model.CartItem
import com.misbah.quickcart.core.data.model.Order
import com.misbah.quickcart.core.data.model.Product
import com.misbah.quickcart.core.data.storage.PreferenceUtils
import com.misbah.quickcart.databinding.FragmentCartDetailsBinding
import com.misbah.quickcart.ui.customs.CartItemLayout
import com.misbah.quickcart.ui.dialogs.TimePickerFragment
import com.misbah.quickcart.ui.listeners.OnDateTimeListener
import com.misbah.quickcart.ui.main.CartViewModel
import com.misbah.quickcart.ui.main.MainActivity
import com.misbah.quickcart.ui.utils.exhaustive
import com.misbah.quickcart.ui.utils.gone
import com.misbah.quickcart.ui.utils.visible
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author: Mohammad Misbah
 * @since: 16-DEC-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
class CartDetailsFragment : BaseFragment<CartDetailsViewModel>(), OnDateTimeListener {
    private val tasksArgs: CartDetailsFragmentArgs by navArgs()
    private var _binding: FragmentCartDetailsBinding? = null
    internal lateinit var viewModel: CartDetailsViewModel

    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val binding get() = _binding!!
    private val sharedViewModel: CartViewModel by activityViewModels()
    override fun getViewModel(): CartDetailsViewModel {
        viewModel = ViewModelProvider(viewModelStore, factory)[CartDetailsViewModel::class.java]
        return viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCartDetailsBinding.inflate(inflater, container, false)
        viewModel.order.value = tasksArgs.order
        binding.farg = this@CartDetailsFragment
        binding.cartViewModel = sharedViewModel
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            if (viewModel.order.value != null) {
                sharedViewModel.shoppingCart.value!!.clearCart()
                for (data in viewModel.order.value?.getCartList()!!) {
                    val product = Product(name = data.name, price = data.price)
                    sharedViewModel.shoppingCart.value!!.addToCart(product, data.quantity)
                }
            }
            layoutCartItem.removeAllViews()
            for (data in sharedViewModel.shoppingCart.value!!.getCartItems()) {
                val layout = CartItemLayout(context)
                layout.setData(data.key, data.value)
                val cartItem = CartItem(
                    productId = data.key.id,
                    name = data.key.name,
                    quantity = data.value,
                    price = data.key.price,
                    totalPrice = data.value * data.key.price
                )
                sharedViewModel.cartList.value?.add(cartItem)
                layoutCartItem.addView(layout)
            }
            val taxStatus =
                context?.let { PreferenceUtils.with(it).getBoolean("tax_inclusive", false) }!!
            val order = Order(
                carts = "",
                amount = sharedViewModel.shoppingCart.value?.getSubtotal()!!,
                taxAmount = sharedViewModel.shoppingCart.value?.getTaxAmount()!!,
                totalAmout = sharedViewModel.shoppingCart.value?.getTotalPrice()!!,
                taxIncluded = taxStatus
            )
            order.carts = order.toCartLisString(sharedViewModel.cartList.value!!)
            if (tasksArgs.order != null) {
                layoutPlaceOrder.gone()
            } else {
                layoutPlaceOrder.visible()
            }
            if (viewModel.order.value == null)
                viewModel.order.value = order
        }
        @Suppress("IMPLICIT_CAST_TO_ANY")
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.addEditTaskEvent.collect { event ->
                    when (event) {
                        is CartDetailsViewModel.AddEditTaskEvent.ShowInvalidInputMessage -> {
                            Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_LONG).show()
                        }

                        is CartDetailsViewModel.AddEditTaskEvent.NavigateBackWithResult -> {
                            setFragmentResult(
                                "add_edit_request",
                                bundleOf("add_edit_result" to event.result)
                            )
                            sharedViewModel.shoppingCart.value?.clearCart()
                            findNavController().popBackStack()
                        }

                        is CartDetailsViewModel.AddEditTaskEvent.ShowDateTimePicker -> {
                            TimePickerFragment.newInstance(this@CartDetailsFragment)
                                .show(childFragmentManager, "timePicker")
                        }

                        else -> {}
                    }.exhaustive
                }
            }
        }
        (requireActivity() as MainActivity).hideFAB()
    }

    fun clickOnSave() {
        viewModel.onPlaceOrderClick()
    }

    override fun onDestroy() {
        super.onDestroy()
        sharedViewModel.cartList.value?.clear()
        if (tasksArgs.order != null) {
            sharedViewModel.shoppingCart.value?.clearCart()
        }
        viewModel.order.value = null
    }

    override fun onDateTimeSelected(timestamp: Long) {
        viewModel.onDateTimeResult(timestamp)
    }
}