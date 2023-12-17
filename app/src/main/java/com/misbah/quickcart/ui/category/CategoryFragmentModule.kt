package com.misbah.quickcart.ui.category

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import com.misbah.quickcart.core.data.storage.PreferencesManager
import com.misbah.quickcart.core.data.storage.QuickCartDao
import com.misbah.quickcart.core.di.factory.ViewModelFactory
import com.misbah.quickcart.ui.products.ProductViewModel
import dagger.Module
import dagger.Provides

/**
 * @author: Mohammad Misbah
 * @since: 16-Dec-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
@Module
class CategoryFragmentModule {

    @Provides
    fun provideViewModel(quickCartDao : QuickCartDao, preferencesManager : PreferencesManager,  state : SavedStateHandle) : CategoryViewModel {
        return CategoryViewModel(quickCartDao, preferencesManager)
    }

    @Provides
    fun provideViewModelProvider(viewModel: CategoryViewModel) : ViewModelProvider.Factory{
        return ViewModelFactory(viewModel)
    }

}