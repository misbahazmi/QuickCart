package com.misbah.quickcart.ui.settings

import android.os.Bundle
import androidx.fragment.app.activityViewModels
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.SwitchPreferenceCompat
import com.misbah.quickcart.R
import com.misbah.quickcart.core.data.storage.PreferenceUtils
import com.misbah.quickcart.ui.main.CartViewModel
import com.misbah.quickcart.ui.utils.Utils
import com.nytimes.utils.AppLog
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

    private lateinit var preferenceUtils : PreferenceUtils

    private val sharedViewModel: CartViewModel by activityViewModels()

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.preference, rootKey)
        preferenceUtils = context?.let { PreferenceUtils.with(it) }!!
        val taxStatus = findPreference<SwitchPreferenceCompat>("tax_status")
        taxStatus?.setOnPreferenceChangeListener { _, newValue ->
            AppLog.debugD("Tax Inclusive: ${newValue}")
            preferenceUtils.save("tax_inclusive", newValue as Boolean)
            true
        }
        taxStatus?.setDefaultValue( preferenceUtils.getBoolean("tax_inclusive", true))
    }
}
