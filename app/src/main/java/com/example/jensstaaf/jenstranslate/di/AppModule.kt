package com.example.jensstaaf.jenstranslate.di

import android.content.Context
import com.example.jensstaaf.jenstranslate.App
import dagger.Binds
import dagger.Module
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Qualifier
import javax.inject.Singleton

@Qualifier
@kotlin.annotation.Retention(AnnotationRetention.RUNTIME)
annotation class AppContext

@Module(includes = [(AndroidSupportInjectionModule::class)])
abstract class AppModule {

    @Singleton
    @Binds
    @AppContext
    abstract fun provideContext(app: App): Context
}