package com.trycrescendo.reduxdemoapp.rx.redux

import io.reactivex.*
import io.reactivex.functions.Function

/**
 * Created by stephen on 10/9/17.
 */

abstract class StateAccumulator<Upstream, Downstream> constructor(private val initialValue: Downstream) : FlowableTransformer<Upstream, UiState<Downstream>> {

    abstract fun actionMapper(): Function<Upstream, Flowable<UiState<Downstream>>>
    
    override fun apply(upstream: Flowable<Upstream>): Flowable<UiState<Downstream>> {
        return upstream
                .flatMap(actionMapper())
                .distinctUntilChanged()
                .onErrorReturn(errorMapper())
                .startWith(UiState.idle(initialValue))
    }
    
    fun errorMapper(): Function<Throwable, UiState<Downstream>> = Function { t: Throwable -> UiState.error<Downstream>(t) }

}