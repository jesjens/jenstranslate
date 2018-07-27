package com.example.jensstaaf.jenstranslate.di

import com.example.jensstaaf.jenstranslate.App
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules = [
        (AndroidSupportInjectionModule::class),
        (AppModule::class),
        (ActivitiesModule::class),
        (ConfigModule::class)])
interface AppComponent : AndroidInjector<App> {
    @Component.Builder
    abstract class Builder : AndroidInjector.Builder<App>()
}