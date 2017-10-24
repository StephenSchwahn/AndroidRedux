package com.trycrescendo.reduxdemoapp.rx

import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by eric on 9/11/17.
 */
class AppSchedulersProvider : SchedulersProvider {
    override fun main(): Scheduler = AndroidSchedulers.mainThread()

    override fun io(): Scheduler = Schedulers.io()

    override fun trampoline(): Scheduler = Schedulers.trampoline()
}
