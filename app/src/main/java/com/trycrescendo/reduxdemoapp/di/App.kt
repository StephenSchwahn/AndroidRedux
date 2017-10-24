package com.trycrescendo.reduxdemoapp.di

import com.squareup.leakcanary.LeakCanary
import dagger.android.support.DaggerApplication
import javax.inject.Inject

/**
 * Created by stephen on 10/23/17.
 */
class App : DaggerApplication() {

    override fun applicationInjector() = DaggerAppComponent
            .builder()
            .application(this)
            .build()


    override fun onCreate() {
        super.onCreate()
        if (LeakCanary.isInAnalyzerProcess(this)) {
            // This process is dedicated to LeakCanary for heap analysis.
            // You should not init your app in this process.
            return
        }
        applyAutoInjector()
    }
}