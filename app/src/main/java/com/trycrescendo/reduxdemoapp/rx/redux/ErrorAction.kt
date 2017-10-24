package com.trycrescendo.reduxdemoapp.rx.redux

/**
 * Created by stephen on 10/10/17.
 */
data class ErrorAction(val throwable: Throwable): BaseAction()