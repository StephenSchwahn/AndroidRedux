package com.trycrescendo.reduxdemoapp.login

import com.trycrescendo.reduxdemoapp.data.CredentialsRepository
import com.trycrescendo.reduxdemoapp.rx.SchedulersProvider
import com.trycrescendo.reduxdemoapp.rx.redux.Dispatcher
import com.trycrescendo.reduxdemoapp.rx.redux.UiActionInteractor
import dagger.Provides
import io.reactivex.Flowable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.functions.Consumer
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Created by stephen on 10/23/17.
 */
class LoginInteractor(private val dispatcher: Dispatcher<LoginActions>,
                      private val credentialsRepository: CredentialsRepository,
                      private val schedulersProvider: SchedulersProvider): UiActionInteractor<LoginActions>(){

    val disposeBag = CompositeDisposable()


    override val actionMapper: Consumer<LoginActions> = Consumer { action ->
        when (action) {
            is LoginActions.LoginAction -> tryLogin(action.email, action.pass)
            is LoginActions.RegisterAction -> tryCreateAccount(action.email, action.pass)
        }
    }

    override val errorMapper: Consumer<Throwable> = Consumer { err -> Timber.e(err.localizedMessage) }

    private fun tryLogin(email: String, pass: String) {
        disposeBag.add(credentialsRepository.findCredentials(email, pass)
                .observeOn(schedulersProvider.io())
                .subscribe( { credentials ->
                    dispatcher.dispatch(Flowable.just(LoginActions.LoginActionSuccess(
                            credentials.accessToken!!, credentials.email!!, credentials.pass!!)))
                }, { err ->
                    dispatcher.dispatch(Flowable.just(LoginActions.LoginActionFailure(
                            err, email, pass
                    )))
                })
        )
    }

    private fun tryCreateAccount(email: String, pass: String) {
        disposeBag.add(credentialsRepository.saveCredentials(email, pass)
                .subscribe( {
                    dispatcher.dispatch(Flowable.just(LoginActions.RegisterActionSuccess(email, pass)))
                }, { err ->
                    dispatcher.dispatch(Flowable.just(LoginActions.RegisterActionFailure(err, email, pass)))
                })
        )
    }
}