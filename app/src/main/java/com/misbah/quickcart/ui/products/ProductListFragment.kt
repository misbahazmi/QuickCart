package com.misbah.quickcart.ui.products

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
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.material.snackbar.Snackbar
import com.misbah.chips.ChipListener
import com.misbah.quickcart.core.base.BaseFragment
import com.misbah.quickcart.core.data.model.Category
import com.misbah.quickcart.core.data.model.Product
import com.misbah.quickcart.databinding.FragmentCategoryBinding
import com.misbah.quickcart.databinding.FragmentProductsBinding
import com.misbah.quickcart.ui.adapters.CategoryAdapter
import com.misbah.quickcart.ui.adapters.ProductAdapter
import com.misbah.quickcart.ui.listeners.OnProductClickListener
import com.misbah.quickcart.ui.main.CartViewModel
import com.misbah.quickcart.ui.main.MainActivity
import com.misbah.quickcart.ui.orders.OrderListViewModel
import com.misbah.quickcart.ui.utils.exhaustive
import com.nytimes.utils.AppEnums
import kotlinx.coroutines.flow.first
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
class ProductListFragment : BaseFragment<ProductViewModel>(), OnProductClickListener {

    private var _binding: FragmentProductsBinding? = null
    internal lateinit var viewModel: ProductViewModel
    @Inject
    lateinit var factory: ViewModelProvider.Factory
    private val binding get() = _binding!!

    private val sharedViewModel: CartViewModel by activityViewModels()

    override fun getViewModel(): ProductViewModel {
        viewModel = ViewModelProvider(viewModelStore, factory)[ProductViewModel::class.java]
        return viewModel
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProductsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val productAdapter =  ProductAdapter(this@ProductListFragment)
        binding.apply {
            val catList = arrayListOf<Category>()
            for ((index,data)  in AppEnums.ProductCategory.values().distinct().withIndex()){
                catList.add(Category(data.value, data.name))
            }
            for (data in catList){
                chipTasksCategory.addChip(data.name)
            }
            chipTasksCategory.updateHorizontalSpacing()
            chipTasksCategory.setChipListener( object : ChipListener {
                override fun chipSelected(index: Int) {
                    val catId =  catList[index].id
                    viewModel.onFilterCategoryClick(catId)
                }
                override fun chipDeselected(index: Int) {}
                override fun chipRemoved(index: Int) {}
            }
            )
            recyclerViewProducts.apply {
                adapter = productAdapter
                layoutManager = LinearLayoutManager(requireContext())
                setHasFixedSize(true)
            }
        }
        viewModel.products.observe(viewLifecycleOwner) {
            productAdapter.submitList(it)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.productEvent.collect { event ->
                    when (event) {
                        is ProductViewModel.ProductEvent.NavigateToAddProductDialog -> {
                            val action = ProductListFragmentDirections.actionAddCategoryDialogFragment()
                            findNavController().navigate(action)
                        }
                        is ProductViewModel.ProductEvent.ShowManageProductMessage ->{
                            Snackbar.make(requireView(), event.msg, Snackbar.LENGTH_SHORT).show()
                        }
                        is ProductViewModel.ProductEvent.ShowUndoDeleteTaskMessage -> {
                            Snackbar.make(requireView(), "Product deleted", Snackbar.LENGTH_LONG)
                                .setAction("UNDO") {
                                    viewModel.onUndoDeleteClick(event.product)
                                }.show()
                        }
                        else ->{}
                    }.exhaustive
                }
            }
        }
        viewLifecycleOwner.lifecycleScope.launch {
            if(viewModel.preferencesFlow.first().category != 0)
                viewModel.preferencesFlow.first().category.let { binding.chipTasksCategory.setSelectedChip(it) }
            else
                binding.chipTasksCategory.setSelectedChip(0)
        }
        (requireActivity() as MainActivity).showFAB()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    override fun onItemClick(product : Product) {}

    override fun onItemDeleteClick(product : Product) {
        viewModel.deleteProduct(product)

    }

    override fun onItemEditClick(product : Product) {
        viewModel.displayMessage("To Be Implemented")
    }

    override fun onAddToCartClick(product : Product) {
        sharedViewModel.shoppingCart.value!!.addToCart(product, 1)
    }
}