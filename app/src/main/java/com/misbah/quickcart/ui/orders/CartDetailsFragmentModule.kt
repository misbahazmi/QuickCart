package com.misbah.quickcart.ui.orders

import android.content.Context
import androidx.lifecycle.ViewModelProvider
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
class CartDetailsFragmentModule {

    @Provides
    fun provideViewModel(quickCartDao : QuickCartDao, state : Context) : CartDetailsViewModel {
        return CartDetailsViewModel(quickCartDao, state)
    }

    @Provides
    fun provideViewModelProvider(viewModel: CartDetailsViewModel) : ViewModelProvider.Factory{
        return ViewModelFactory(viewModel)
    }

}