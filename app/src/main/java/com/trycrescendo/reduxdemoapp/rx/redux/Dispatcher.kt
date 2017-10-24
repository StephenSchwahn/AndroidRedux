package com.trycrescendo.reduxdemoapp.rx.redux

import io.reactivex.Flowable

/**
 * Created by stephen on 10/9/17.
 */
interface Dispatcher<R> {
    fun dispatch(dispatched: Flowable<R>)
//    fun dispatch(dispatched: R)
}