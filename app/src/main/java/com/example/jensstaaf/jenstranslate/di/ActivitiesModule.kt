package com.example.jensstaaf.jenstranslate.di

import com.example.jensstaaf.jenstranslate.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivitiesModule {

    @ContributesAndroidInjector
    abstract fun contributeMainActivityInjector(): MainActivity
}