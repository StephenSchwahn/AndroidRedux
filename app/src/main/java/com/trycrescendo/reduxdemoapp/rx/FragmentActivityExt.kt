package com.trycrescendo.reduxdemoapp.rx

import android.app.Activity
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentActivity
import android.view.inputmethod.InputMethodManager
import com.trycrescendo.reduxdemoapp.R

/**
 * Created by stephen on 10/23/17.
 */
fun FragmentActivity.openFragment(fragment: Fragment?, container: Int = R.id.container, tag: String? = null) {
    this.supportFragmentManager
            .beginTransaction()
            .add(container, fragment, tag)
            .commit();
}

inline fun FragmentActivity.replaceFragment(container: Int = R.id.container, f: () -> Fragment): Fragment? {
    val manager = supportFragmentManager
    return f().apply {
        manager
                .beginTransaction()
                .addToBackStack(null)
                .replace(container, f(), null).commit()
    }
}

inline fun FragmentActivity.setContentFragment(containerViewId: Int, f: () -> Fragment): Fragment? {
    val manager = supportFragmentManager
    val fragment = manager?.findFragmentById(containerViewId)
    fragment?.let { return it }
    return f().apply { manager?.beginTransaction()?.add(containerViewId, this)?.commit() }
}

fun Activity.hideKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    currentFocus?.let {
        imm.hideSoftInputFromWindow(currentFocus?.windowToken, 0)
    }
}

fun Activity.showKeyboard() {
    val imm = this.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    currentFocus?.let {
        imm.showSoftInput(currentFocus, 0)
    }

}