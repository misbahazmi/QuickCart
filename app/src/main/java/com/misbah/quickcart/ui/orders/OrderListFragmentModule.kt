package com.misbah.quickcart.ui.orders

import android.content.Context
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModelProvider
import com.misbah.quickcart.core.data.storage.PreferencesManager
import com.misbah.quickcart.core.data.storage.QuickCartDao
import com.misbah.quickcart.core.di.factory.ViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * @author: Mohammad Misbah
 * @since: 16-DEC-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
@Module
class OrderListFragmentModule {

    @Provides
    fun provideViewModel(quickCartDao : QuickCartDao, preferencesManager: PreferencesManager, state : SavedStateHandle, context: Context) : OrderListViewModel {
        return OrderListViewModel(quickCartDao, preferencesManager, state, context)
    }

    @Provides
    fun provideViewModelProvider(viewModel: OrderListViewModel) : ViewModelProvider.Factory{
        return ViewModelFactory(viewModel)
    }

}