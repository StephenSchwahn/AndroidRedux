package com.trycrescendo.reduxdemoapp.ui.base

import com.trycrescendo.reduxdemoapp.rx.SchedulersProvider
import com.trycrescendo.reduxdemoapp.rx.redux.*
import io.reactivex.*
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Consumer
import io.reactivex.processors.BehaviorProcessor
import io.reactivex.processors.PublishProcessor
import io.reactivex.rxkotlin.addTo
import io.reactivex.subjects.Subject
import org.reactivestreams.Processor
import timber.log.Timber

abstract class DispatchingViewModel<DataType, R: BaseAction>(protected var interactors: List<UiActionInteractor<R>>, private val schedulers: SchedulersProvider):
        RxViewModel(), Dispatcher<R> {

    // Private values for the set of interactors and actions
    private var actions: PublishProcessor<R> = PublishProcessor.create()

    // Overridables
    abstract protected val defaultState: UiState<DataType>
    abstract protected val accumulator: StateAccumulator<R, DataType>

    val disposables = CompositeDisposable()

    // Observable state
    lateinit var uiModel: BehaviorProcessor<UiState<DataType>>

    fun init() {
        uiModel = BehaviorProcessor.createDefault(defaultState)

        actions
                .subscribeOn(schedulers.io())
                .observeOn(schedulers.io())
                .doOnNext(ListInteractor())
                .compose(accumulator)
                .observeOn(schedulers.main())
                .subscribe({ uiModel.onNext(it) }, { uiModel.onNext(UiState.error(it)) })
                .addTo(compositeDisposable)
    }

    override fun dispatch(dispatched: Flowable<R>) {
        dispatched.forEach {
            schedulers.trampoline().scheduleDirect { actions.onNext(it) }
        }
    }

    private inner class ListInteractor : Consumer<R> {

        override fun accept(action: R) {

            interactors.forEach {
                try {
                    schedulers.io().scheduleDirect { it.accept(action) }
                } catch (e: Exception) {
                    Timber.e(e) // Each consumer manually handles errors
                }
            }
        }

    }
}