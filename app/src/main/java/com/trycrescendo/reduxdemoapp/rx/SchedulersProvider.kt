package com.trycrescendo.reduxdemoapp.rx

import io.reactivex.Scheduler


interface SchedulersProvider {
    fun main(): Scheduler
    fun io(): Scheduler
    fun trampoline(): Scheduler
}