package com.misbah.quickcart.core.di.component

import com.misbah.quickcart.core.di.builder.FragmentBuilder
import com.misbah.quickcart.core.di.module.NetworkModule
import com.misbah.quickcart.ui.QuickCartApplication
import com.misbah.quickcart.core.di.builder.ActivityBuilder
import com.misbah.quickcart.core.di.builder.BroadcastReceiverModule
import com.misbah.quickcart.core.di.module.AppModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        ActivityBuilder::class,
        FragmentBuilder::class,
        BroadcastReceiverModule::class
    ]
)

interface MainComponent : AndroidInjector<QuickCartApplication> {
    @Component.Builder
    interface Builder{
        @BindsInstance
        fun application(application: QuickCartApplication): Builder
        fun build(): MainComponent
    }
}