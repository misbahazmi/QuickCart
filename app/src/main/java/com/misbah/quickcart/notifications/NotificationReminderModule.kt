package com.misbah.quickcart.notifications

import com.misbah.quickcart.core.data.storage.QuickCartDao
import dagger.Module
import dagger.Provides

/**
 * @author: Mohammad Misbah
 * @since: 17-Dec-2023
 * @sample: Technology Assessment for Sr. Android Role
 * Email Id: mohammadmisbahazmi@gmail.com
 * GitHub: https://github.com/misbahazmi
 * Expertise: Android||Java/Kotlin||Flutter
 */
@Module
class NotificationReminderModule {
    @Provides
    fun provideTaskDataRepository(quickCartDao : QuickCartDao) : TaskDataRepository {
        return TaskDataRepository(quickCartDao)
    }
}