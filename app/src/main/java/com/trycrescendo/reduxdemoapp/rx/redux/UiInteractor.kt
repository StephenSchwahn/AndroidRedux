package com.trycrescendo.reduxdemoapp.rx.redux

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Single
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import org.intellij.lang.annotations.Flow
import org.reactivestreams.Publisher


/**
 * Created by eric on 9/8/17.
 */
abstract class UiInteractor<Upstream, Downstream> constructor(protected val initialValue: Downstream)
    : FlowableTransformer<Upstream, Downstream> {


    abstract fun actionMapper(): Function<Upstream, Flowable<Downstream>>
    override fun apply(upstream: Flowable<Upstream>): Publisher<Downstream> {
        return upstream
                .flatMap(actionMapper())
                .distinctUntilChanged()
                .onErrorResumeNext(Flowable.just(initialValue)) // Just pass this value along
                .startWith(initialValue)
    }
}

// Allow a specific class of actions in (as a receiver), but allow output of any new actions
abstract class UiActionInteractor<T>: Consumer<T> {

    abstract val actionMapper: Consumer<T>
    abstract val errorMapper: Consumer<Throwable>
    override fun accept(action: T) {
        Single.just(action).subscribe(actionMapper, errorMapper)
    }
}