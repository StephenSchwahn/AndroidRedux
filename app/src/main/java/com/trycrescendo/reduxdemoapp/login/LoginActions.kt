package com.trycrescendo.reduxdemoapp.login

import com.trycrescendo.reduxdemoapp.rx.redux.BaseAction
import com.trycrescendo.reduxdemoapp.ui.navigation.NavigationAction

sealed class LoginActions: BaseAction() {

    class Noop: LoginActions()

    data class LoginAction(val email: String, val pass: String): LoginActions()
    data class LoginActionSuccess(val authToken: String, val email: String, val pass: String): LoginActions()
    data class LoginActionFailure(val err: Throwable, val email: String, val pass: String): LoginActions()
    data class RegisterAction(val email: String, val pass: String): LoginActions()
    data class RegisterActionSuccess(val email: String, val pass: String): LoginActions()
    data class RegisterActionFailure(val err: Throwable, val email: String, val pass: String): LoginActions()

    class MoveToHomeAction: LoginActions(), NavigationAction
}
