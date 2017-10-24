package com.trycrescendo.reduxdemoapp.di

import dagger.Module
import dagger.Provides
import javax.inject.Named
import javax.inject.Singleton
import android.content.SharedPreferences
import android.preference.PreferenceManager
import com.trycrescendo.reduxdemoapp.rx.AppSchedulersProvider
import com.trycrescendo.reduxdemoapp.rx.SchedulersProvider

/**
 * Created by eric on 9/4/17.
 */


@Module(includes = arrayOf(DataModule::class))
class AppModule {

    @Provides
    @Singleton
    fun preferences(application: App): SharedPreferences = PreferenceManager.getDefaultSharedPreferences(application)

    @Provides
    @Singleton
    fun schedulers(): SchedulersProvider = AppSchedulersProvider()

}