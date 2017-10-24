package com.trycrescendo.reduxdemoapp.ui.navigation

import com.trycrescendo.reduxdemoapp.rx.redux.Dispatcher

interface Navigator {
    fun applyNavigation(navigationAction: NavigationAction, dispatch: Dispatcher<*>)
}