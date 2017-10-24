package com.trycrescendo.reduxdemoapp.ui.base

import com.trycrescendo.reduxdemoapp.rx.SchedulersProvider
import com.trycrescendo.reduxdemoapp.rx.redux.BaseAction
import com.trycrescendo.reduxdemoapp.rx.redux.UiActionInteractor
import com.trycrescendo.reduxdemoapp.ui.navigation.NavigationAction
import com.trycrescendo.reduxdemoapp.ui.navigation.Navigator
import io.reactivex.Flowable
import io.reactivex.functions.Consumer
import io.reactivex.functions.Function
import timber.log.Timber

/**
 * Created by stephen on 10/9/17.
 */
abstract class NavigationViewModel<DataType, R: BaseAction>(interactors: List<UiActionInteractor<R>>, schedulers: SchedulersProvider, val baseAction: R):
        DispatchingViewModel<DataType, R>(interactors, schedulers) {

    var navigationInteractor: NavigationInteractor = NavigationInteractor()
    var navigation: Navigator? = null

    fun assignNavigator(nav: Navigator) {
        this.navigation = nav
    }

    fun unassignNavigator() {
        this.navigation = null
    }

    init {
        val list = mutableListOf<UiActionInteractor<R>>(navigationInteractor)
        list.addAll(interactors)
        super.interactors = list
    }

    inner class NavigationInteractor: UiActionInteractor<R>() {
        override val actionMapper: Consumer<R> = Consumer { action ->
            when (action) {
                is NavigationAction -> {
                    navigation?.applyNavigation(action, this@NavigationViewModel)
                }
            }
        }

        override val errorMapper: Consumer<Throwable> = Consumer {  }
    }
}