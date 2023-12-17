package com.misbah.quickcart.ui.products

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import com.misbah.quickcart.core.data.storage.PreferencesManager
import com.misbah.quickcart.core.data.storage.QuickCartDao
import com.misbah.quickcart.core.di.factory.ViewModelFactory
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
class ProductFragmentModule {

    @Provides
    fun provideViewModel(quickCartDao : QuickCartDao, preferencesManager : PreferencesManager,  state : SavedStateHandle) : ProductViewModel {
        return ProductViewModel(quickCartDao, preferencesManager, state)
    }

    @Provides
    fun provideViewModelProvider(viewModel: ProductViewModel) : ViewModelProvider.Factory{
        return ViewModelFactory(viewModel)
    }

}