package com.trycrescendo.reduxdemoapp.ui.base

import android.os.Bundle
import android.support.v4.app.Fragment
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import com.trello.rxlifecycle2.components.support.RxFragmentActivity
import dagger.android.AndroidInjection
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

/**
 * Created by eric on 9/6/17.
 */
abstract class BaseActivity : RxFragmentActivity(), HasSupportFragmentInjector {

    @Inject lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    protected val compositeDisposable by lazy { CompositeDisposable() }

    override fun onDestroy() {
        dispose()
        super.onDestroy()
    }

    private fun dispose() {
        compositeDisposable.clear()
    }

    override fun supportFragmentInjector() = fragmentInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
    }


}