package com.example.jensstaaf.jenstranslate.di

import com.example.jensstaaf.jenstranslate.App
import com.example.jensstaaf.jenstranslate.PropertyLoader
import dagger.Module
import dagger.Provides
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Module (includes = [(AndroidSupportInjectionModule::class)])
class ConfigModule {

    @Provides
    @Singleton
    fun providesPropertyLoader(app: App): PropertyLoader
    {
        return PropertyLoader(app)
    }
}