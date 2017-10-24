package com.trycrescendo.reduxdemoapp.di

import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.trycrescendo.reduxdemoapp.di.ViewModelFactory
import com.trycrescendo.reduxdemoapp.di.ViewModelKey
import com.trycrescendo.reduxdemoapp.login.LoginActions
import com.trycrescendo.reduxdemoapp.login.LoginFragment
import com.trycrescendo.reduxdemoapp.login.LoginViewModel
import com.trycrescendo.reduxdemoapp.rx.redux.Dispatcher
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.android.ContributesAndroidInjector
import dagger.multibindings.IntoMap
import javax.inject.Singleton

/**
 * Created by stephen on 10/23/17.
 */
@Module
internal abstract class LoginModule {

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindAuthViewModel(viewModel: LoginViewModel): ViewModel

    @ContributesAndroidInjector
    abstract fun contributeLoginFragment(): LoginFragment
}