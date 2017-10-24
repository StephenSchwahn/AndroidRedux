package com.trycrescendo.reduxdemoapp.ui.base

import android.arch.lifecycle.LifecycleRegistry

import android.content.Context
import android.support.v4.app.Fragment
import com.trello.rxlifecycle2.components.support.RxFragment
import com.trycrescendo.reduxdemoapp.di.Injectable
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by eric on 9/13/17.
 */
abstract class BaseFragment: RxFragment(), HasSupportFragmentInjector, Injectable {

    protected val compositeDisposable by lazy { CompositeDisposable() }

    @Inject lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>

    override fun onAttach(context: Context?) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> = childFragmentInjector

    override fun onDestroy() {
        dispose()
        super.onDestroy()
    }

    private fun dispose() {
        compositeDisposable.clear()
    }
}