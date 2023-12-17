package com.misbah.quickcart.ui.settings

import androidx.lifecycle.ViewModelProvider
import com.misbah.quickcart.core.data.storage.PreferencesManager
import com.misbah.quickcart.core.di.factory.ViewModelFactory
import dagger.Module
import dagger.Provides

/**
 * @author: Mohammad Misbah
 * @since: 26-Sep-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
@Module
class SettingsFragmentModule {

    @Provides
    fun provideViewModel(preferencesManager: PreferencesManager) : SettingsViewModel {
        return SettingsViewModel(preferencesManager)
    }

    @Provides
    fun provideViewModelProvider(viewModel: SettingsViewModel) : ViewModelProvider.Factory{
        return ViewModelFactory(viewModel)
    }

}