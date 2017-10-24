package com.trycrescendo.reduxdemoapp.login

/**
 * Created by stephen on 10/23/17.
 */

data class LoginUiModel (
        val email: String?,
        val password: String?,
        val token: String?,
        val inProgress: Boolean
) {
    companion object {
        fun inProgress(email: String, password: String): LoginUiModel {
            return LoginUiModel(email, password, null, true)
        }

        fun create(email: String, accessToken: String): LoginUiModel {
            return LoginUiModel(email, null, accessToken, false)
        }

        fun default(): LoginUiModel {
            return LoginUiModel(null,null, null, false)
        }
    }
}
