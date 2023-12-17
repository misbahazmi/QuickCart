package com.misbah.quickcart.ui.settings

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.ViewModelProvider
import androidx.preference.PreferenceCategory
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.misbah.quickcart.R
import com.misbah.quickcart.core.data.storage.PreferencesManager
import com.misbah.quickcart.ui.main.CartViewModel
import com.misbah.quickcart.ui.utils.Utils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author: Mohammad Misbah
 * @since: 03-Oct-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
class SettingsFragment : PreferenceFragmentCompat() {
    @Inject
    lateinit var utils: Utils

    @Inject
    lateinit var preferenceManager: PreferencesManager

    private val sharedViewModel: CartViewModel by activityViewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)
        preferenceManager = context?.let { PreferencesManager(it) }!!
        val taxStatus = findPreference<SwitchPreferenceCompat>("tax_status")
        taxStatus?.setOnPreferenceChangeListener { _, newValue ->
            CoroutineScope(Dispatchers.IO).launch {
                preferenceManager.updateTextSatus(newValue as Boolean)
            }
            // Reflect the newValue to Preference?
            true
        }
        val preferencesFlow = preferenceManager.preferencesFlow
        CoroutineScope(Dispatchers.IO).launch {
            taxStatus?.setDefaultValue(preferencesFlow.collect{
                it.taxIncluded
            })
        }
    }
}
