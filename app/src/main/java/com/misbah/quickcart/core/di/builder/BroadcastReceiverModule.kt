package com.misbah.quickcart.core.di.builder

import com.misbah.quickcart.notifications.NotificationReminderModule
import com.misbah.quickcart.notifications.NotificationReminderReceiver
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class BroadcastReceiverModule {
    @ContributesAndroidInjector( modules = [NotificationReminderModule::class])
    abstract fun contributesNotificationReminderReceiver() : NotificationReminderReceiver

}