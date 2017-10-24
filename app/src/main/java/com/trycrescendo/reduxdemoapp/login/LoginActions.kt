package com.trycrescendo.reduxdemoapp.login

import com.trycrescendo.reduxdemoapp.rx.redux.BaseAction
import com.trycrescendo.reduxdemoapp.ui.navigation.NavigationAction

sealed class LoginActions: BaseAction() {

    open class Noop: LoginActions()

    class LoginAction(val email: String, val pass: String): LoginActions()
    class LoginActionSuccess(val authToken: String, val email: String, val pass: String): LoginActions()
    class LoginActionFailure(val err: Throwable, val email: String, val pass: String): LoginActions()
    class RegisterAction(val email: String, val pass: String): LoginActions()
    class RegisterActionSuccess(val email: String, val pass: String): LoginActions()
    class RegisterActionFailure(val err: Throwable, val email: String, val pass: String): LoginActions()

    open class MoveToHomeAction: LoginActions(), NavigationAction
}
