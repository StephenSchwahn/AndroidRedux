package com.trycrescendo.reduxdemoapp.ui.base

import android.arch.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable

/**
 * Created by eric on 9/7/17.
 */
abstract class RxViewModel : ViewModel() {
    protected val compositeDisposable: CompositeDisposable

    init {
        compositeDisposable = CompositeDisposable()

    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}