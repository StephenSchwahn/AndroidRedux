package com.trycrescendo.reduxdemoapp.login

import android.app.FragmentManager
import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.os.Bundle
import com.trycrescendo.reduxdemoapp.R
import com.trycrescendo.reduxdemoapp.rx.redux.Dispatcher
import com.trycrescendo.reduxdemoapp.rx.setContentFragment
import com.trycrescendo.reduxdemoapp.ui.base.BaseActivity
import com.trycrescendo.reduxdemoapp.ui.navigation.NavigationAction
import com.trycrescendo.reduxdemoapp.ui.navigation.Navigator
import dagger.android.AndroidInjection
import dagger.android.support.AndroidSupportInjection
import io.reactivex.Flowable
import io.reactivex.rxkotlin.addTo

import kotlinx.android.synthetic.main.activity_login.*
import timber.log.Timber
import javax.inject.Inject

/**
 * A login screen that offers login via email/password.
 */
class LoginActivity : BaseActivity(), Navigator {

    companion object {
        val TAG = LoginActivity::class.java.simpleName
    }

    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory
    private val authViewModel by lazy { ViewModelProviders.of(this, viewModelFactory).get(LoginViewModel::class.java) }

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        authViewModel.uiModel.subscribe { creds ->
            Timber.e("NEW Credentials: $creds")

        }.addTo(compositeDisposable)

        authViewModel.assignNavigator(this)
        authViewModel.dispatch(Flowable.just(LoginActions.MoveToHomeAction()))
    }

    override fun onDestroy() {
        authViewModel.unassignNavigator()
        super.onDestroy()
    }

    override fun applyNavigation(navigationAction: NavigationAction, dispatch: Dispatcher<*>) {
        when (navigationAction) {
//            is LoginActions.MoveToHomeAction -> openMain()
        }
    }

    private fun openMain() {
//        var homeFragment = supportFragmentManager.findFragmentById(R.id.)
        val homeFragment = LoginFragment()
        if (homeFragment.isHidden) {
            supportFragmentManager.beginTransaction()
                    .show(homeFragment)
                    .commitAllowingStateLoss()
        }
    }
}
