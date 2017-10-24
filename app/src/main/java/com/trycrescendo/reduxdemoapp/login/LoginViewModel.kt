package com.trycrescendo.reduxdemoapp.login

import com.trycrescendo.reduxdemoapp.data.CredentialsRepository
import com.trycrescendo.reduxdemoapp.rx.SchedulersProvider
import com.trycrescendo.reduxdemoapp.rx.redux.StateAccumulator
import com.trycrescendo.reduxdemoapp.rx.redux.UiActionInteractor
import com.trycrescendo.reduxdemoapp.rx.redux.UiState
import com.trycrescendo.reduxdemoapp.ui.base.NavigationViewModel
import io.reactivex.Flowable
import io.reactivex.functions.Function
import javax.inject.Inject

/**
 * Created by stephen on 10/23/17.
 */

class LoginViewModel @Inject constructor(schedulers: SchedulersProvider,
                                         credentialsRepository: CredentialsRepository,
                                         schedulersProvider: SchedulersProvider) :
        NavigationViewModel<LoginUiModel, LoginActions>(listOf(), schedulers, LoginActions.Noop()) {

    override val defaultState: UiState<LoginUiModel> = UiState.idle(LoginUiModel.default())
    override val accumulator: StateAccumulator<LoginActions, LoginUiModel> = LoginStateAccumlator()

    init {
        val list = arrayListOf<UiActionInteractor<LoginActions>>(LoginInteractor(this, credentialsRepository, schedulersProvider))
        list.addAll(super.interactors)
        super.interactors = list
        init()
    }

    inner class LoginStateAccumlator: StateAccumulator<LoginActions, LoginUiModel>(LoginUiModel.default()) {
        override fun actionMapper(): Function<LoginActions, Flowable<UiState<LoginUiModel>>> = Function { action ->
            Flowable.just(when (action) {
                is LoginActions.LoginAction -> UiState.inProgress(LoginUiModel.inProgress(action.email, action.pass))
                is LoginActions.LoginActionSuccess -> UiState.success(LoginUiModel.create(action.email, action.authToken))
                is LoginActions.LoginActionFailure -> UiState.error(action.err)
                is LoginActions.RegisterAction -> UiState.inProgress(LoginUiModel.inProgress(action.email, action.pass))
                is LoginActions.RegisterActionSuccess -> UiState.success(LoginUiModel(email = action.email, password = action.pass, token = null, inProgress = false))
                is LoginActions.RegisterActionFailure -> UiState.error(action.err)
                else -> uiModel.blockingLast(UiState.idle(LoginUiModel.default()))
            })
        }

    }

}