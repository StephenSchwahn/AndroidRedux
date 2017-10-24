package com.trycrescendo.reduxdemoapp

import com.trycrescendo.reduxdemoapp.rx.SchedulersProvider
import io.reactivex.Scheduler
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by stephen on 10/24/17.
 */
class TestSchedulersProvider : SchedulersProvider {
    override fun main(): Scheduler = Schedulers.trampoline()

    override fun io(): Scheduler = Schedulers.trampoline()

    override fun trampoline(): Scheduler = Schedulers.trampoline()
}
