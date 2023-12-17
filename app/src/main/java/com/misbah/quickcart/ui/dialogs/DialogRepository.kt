package com.misbah.quickcart.ui.dialogs
import com.misbah.quickcart.core.data.storage.QuickCartDao
import javax.inject.Inject

/**
 * @author: Mohammad Misbah
 * @since: 15-Dec-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
open class DialogRepository @Inject constructor(private val quickCartDao : QuickCartDao){
    fun deleteAllCompletedTasks(){
        //quickCartDao.deleteCompletedTasks()
    }
}