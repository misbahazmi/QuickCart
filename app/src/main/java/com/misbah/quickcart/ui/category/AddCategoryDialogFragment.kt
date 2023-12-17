package com.misbah.quickcart.ui.category

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.InsetDrawable
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.misbah.quickcart.core.base.BaseFragment
import com.misbah.quickcart.databinding.AddCategoryDialogBinding
import com.misbah.quickcart.ui.utils.Utils
import com.misbah.quickcart.ui.utils.exhaustive
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author: Mohammad Misbah
 * @since: 16-Dec-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
class AddCategoryDialogFragment :  BaseFragment<CategoryViewModel>()  {
    lateinit var binding: AddCategoryDialogBinding
    internal lateinit var viewModel: CategoryViewModel
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    @Inject
    lateinit var utils: Utils

    override fun getViewModel(): CategoryViewModel {
        viewModel = ViewModelProvider(viewModelStore, factory)[CategoryViewModel::class.java]
        return viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = AddCategoryDialogBinding.inflate(inflater, container, false)
        binding.farg = this@AddCategoryDialogFragment
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categoryEvent.collect { event ->
                    when (event) {
                        is CategoryViewModel.CategoryEvent.NavigateToAddCategoryDialog -> {
                            val action = CategoryFragmentDirections.actionAddCategoryDialogFragment()
                            findNavController().navigate(action)
                        }
                        is CategoryViewModel.CategoryEvent.ShowManageCategoryMessage->{
                            Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_SHORT).show()
                        }
                        else ->{}
                    }.exhaustive
                }
            }
        }
    }
    override fun onStart() {
        super.onStart()
        dialog!!.window!!.setLayout(ConstraintLayout.LayoutParams.MATCH_PARENT, ConstraintLayout.LayoutParams.WRAP_CONTENT)
        dialog!!.window!!.setBackgroundDrawable(InsetDrawable(ColorDrawable(Color.TRANSPARENT),48))
    }

    companion object {
        @JvmStatic
        fun newInstance() = AddCategoryDialogFragment()
    }

    fun clickOnCancel() {
        dismiss()
    }



    fun clickOnContinue() {
        viewModel.displayMessage("To Be Implemented")
    }
}