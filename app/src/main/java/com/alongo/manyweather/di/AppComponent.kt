package com.alongo.manyweather.di

import com.alongo.manyweather.AppDelegate
import dagger.BindsInstance
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [AppModule::class,
        AndroidSupportInjectionModule::class,
        ActivityBuilderModule::class,
        DataModule::class,
        DomainModule::class,
        ViewModelModule::class]
)
interface AppComponent {

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: AppDelegate): Builder

        fun build(): AppComponent
    }

    fun inject(app: AppDelegate)
}