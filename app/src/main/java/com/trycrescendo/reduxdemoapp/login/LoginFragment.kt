package com.trycrescendo.reduxdemoapp.login

import android.arch.lifecycle.ViewModelProvider
import android.arch.lifecycle.ViewModelProviders
import android.content.Context
import android.os.Bundle
import android.widget.Toast
import com.jakewharton.rxbinding2.view.RxView
import com.trycrescendo.reduxdemoapp.R
import com.trycrescendo.reduxdemoapp.databinding.FragmentLoginBinding
import com.trycrescendo.reduxdemoapp.rx.SchedulersProvider
import com.trycrescendo.reduxdemoapp.rx.hideKeyboard
import com.trycrescendo.reduxdemoapp.rx.redux.UiState
import com.trycrescendo.reduxdemoapp.ui.base.DataBindingFragment
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.rxkotlin.addTo
import timber.log.Timber
import com.trycrescendo.reduxdemoapp.rx.redux.State
import dagger.android.support.AndroidSupportInjection
import java.util.concurrent.TimeUnit
import javax.inject.Inject

/**
 * Created by stephen on 10/23/17.
 */
class LoginFragment: DataBindingFragment<FragmentLoginBinding>(R.layout.fragment_login) {

    @Inject lateinit var schedulers: SchedulersProvider
    @Inject lateinit var viewModelFactory: ViewModelProvider.Factory

    private val viewModel by lazy { ViewModelProviders.of(activity, viewModelFactory).get(LoginViewModel::class.java) }

    companion object {
        private val TAG = LoginFragment::class.java.simpleName
        fun newInstance() = LoginFragment()

    }

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        compositeDisposable.add(viewModel.uiModel.subscribe { model ->
            model?.let {
//                binding.uiState = model.data
                binding.executePendingBindings()
                updateView(model)
            }
        })

        subscribe()
    }

    private fun subscribe() {

        val signInButton = RxView.clicks(binding.emailSignInButton!!)
                .throttleLast(100, TimeUnit.MILLISECONDS)
                .debounce(200, TimeUnit.MILLISECONDS)
                .map { _ -> LoginActions.LoginAction(binding.email.text.toString(), binding.password.text.toString()) }
                .doOnEach { _ -> activity?.hideKeyboard() }

        val signUpButton = RxView.clicks(binding.emailSignUpButton!!)
                .throttleLast(100, TimeUnit.MILLISECONDS)
                .debounce(200, TimeUnit.MILLISECONDS)
                .map { _ -> LoginActions.RegisterAction(binding.email.text.toString(), binding.password.text.toString()) }

        val actions = Observable.merge(signInButton, signUpButton)//, doneKeyboard);
        actions
                .subscribeOn(schedulers.main())
                .observeOn(schedulers.main())
                .subscribe { action -> viewModel.dispatch(Flowable.just(action)) }
                .addTo(compositeDisposable)
    }

    fun updateView(model: UiState<LoginUiModel>?) {
        model?.let { m ->
            Timber.tag(TAG).e("State ${m.state}")
            if (m.state == State.IN_PROGRESS) {
                Toast.makeText(context, "Action in progress", Toast.LENGTH_SHORT).show()
            }

            if (m.state == State.ERROR) {
                val errorMessage = m.error?.localizedMessage
                Toast.makeText(context, "Error $errorMessage", Toast.LENGTH_LONG).show()
            }

            if (State.SUCCESS == m.state) {
                m.data?.let { d ->
                    Toast.makeText(context, m.data.email, Toast.LENGTH_LONG).show()
                }
            }
        }
    }
}