package com.misbah.quickcart.ui.products

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.addTextChangedListener
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.misbah.chips.ChipListener
import com.misbah.quickcart.R
import com.misbah.quickcart.core.base.BaseFragment
import com.misbah.quickcart.databinding.AddProductDialogBinding
import com.misbah.quickcart.ui.utils.Utils
import com.misbah.quickcart.ui.utils.exhaustive
import com.nytimes.utils.AppEnums
import com.nytimes.utils.AppLog
import kotlinx.coroutines.launch
import java.text.DateFormat
import javax.inject.Inject

/**
 * @author: Mohammad Misbah
 * @since: 16-Dec-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
class AddProductDialogFragment : BaseFragment<ProductViewModel>() {
    lateinit var binding: AddProductDialogBinding
    internal lateinit var viewModel: ProductViewModel

    @Inject
    lateinit var factory: ViewModelProvider.Factory

    @Inject
    lateinit var utils: Utils

    override fun getViewModel(): ProductViewModel {
        viewModel = ViewModelProvider(viewModelStore, factory)[ProductViewModel::class.java]
        return viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddProductDialogBinding.inflate(inflater, container, false)
        binding.farg = this@AddProductDialogFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            editTextName.addTextChangedListener {
                viewModel.productTitle = it.toString()
            }
            editTextPrice.addTextChangedListener {
                if (it != null) {
                    if(it.isNotEmpty())
                        viewModel.productPrice = it.toString().toDouble()
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productEvent.collect { event ->
                    when (event) {
                        is ProductViewModel.ProductEvent.NavigateToAddProductDialog -> {
                            val action =
                                ProductListFragmentDirections.actionAddCategoryDialogFragment()
                            findNavController().navigate(action)
                        }
                        is ProductViewModel.ProductEvent.ShowManageProductMessage -> {
                            Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_SHORT).show()
                        }
                        is ProductViewModel.ProductEvent.NavigateBackWithResult -> {
                            AppLog.debugD("RESULT: ${event.result}")
                            findNavController().popBackStack()
                        }
                        else -> {}
                    }.exhaustive
                }
            }
        }
    }

    override fun onStart() {
        super.onStart()
        dialog!!.window!!.setLayout(
            ConstraintLayout.LayoutParams.MATCH_PARENT,
            ConstraintLayout.LayoutParams.WRAP_CONTENT
        )
        dialog!!.window!!.setBackgroundDrawable(InsetDrawable(ColorDrawable(Color.TRANSPARENT), 48))
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddProductDialogFragment()
    }

    fun clickOnCancel() {
        dismiss()
    }

    fun clickOnContinue() {
        viewModel.onSaveClick()
    }
}