package com.misbah.quickcart.core.di.builder


import com.misbah.quickcart.ui.category.AddCategoryDialogFragment
import com.misbah.quickcart.ui.category.CategoryFragment
import com.misbah.quickcart.ui.category.CategoryFragmentModule
import com.misbah.quickcart.ui.products.AddProductDialogFragment
import com.misbah.quickcart.ui.orders.CartDetailsFragmentModule
import com.misbah.quickcart.ui.orders.CartDetailsFragment
import com.misbah.quickcart.ui.products.ProductListFragment
import com.misbah.quickcart.ui.products.ProductFragmentModule
import com.misbah.quickcart.ui.dialogs.ConfirmationDialogFragment
import com.misbah.quickcart.ui.dialogs.DialogFragmentModule
import com.misbah.quickcart.ui.dialogs.QuitDialogFragment
import com.misbah.quickcart.ui.dialogs.TimePickerFragment
import com.misbah.quickcart.ui.settings.SettingsFragment
import com.misbah.quickcart.ui.settings.SettingsFragmentModule
import com.misbah.quickcart.ui.orders.OrderListFragment
import com.misbah.quickcart.ui.orders.OrderListFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

/**
 * @author: Mohammad Misbah
 * @since: 16-Dec-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
@Module
abstract class FragmentBuilder {
    @ContributesAndroidInjector(modules = [OrderListFragmentModule::class])
    abstract fun contributeTasksFragment() : OrderListFragment
    @ContributesAndroidInjector(modules = [CartDetailsFragmentModule::class])
    abstract fun contributeAddEditTaskFragment() : CartDetailsFragment
    @ContributesAndroidInjector(modules = [DialogFragmentModule::class])
    abstract fun contributeDialogFragment() : QuitDialogFragment
    @ContributesAndroidInjector(modules = [DialogFragmentModule::class])
    abstract fun contributeQuitDialogFragment() : ConfirmationDialogFragment
    @ContributesAndroidInjector(modules = [DialogFragmentModule::class])
    abstract fun contributeTimePickerFragment() : TimePickerFragment
    @ContributesAndroidInjector(modules = [SettingsFragmentModule::class])
    abstract fun contributeSettingsFragment() : SettingsFragment
    @ContributesAndroidInjector(modules = [ProductFragmentModule::class])
    abstract fun contributeProductFragment() : ProductListFragment
    @ContributesAndroidInjector(modules = [ProductFragmentModule::class])
    abstract fun contributeAddProductDialogFragment() : AddProductDialogFragment
    @ContributesAndroidInjector(modules = [CategoryFragmentModule::class])
    abstract fun contributeCategoryFragment() : CategoryFragment
    @ContributesAndroidInjector(modules = [CategoryFragmentModule::class])
    abstract fun contributeAddCategoryDialogFragment() : AddCategoryDialogFragment
}