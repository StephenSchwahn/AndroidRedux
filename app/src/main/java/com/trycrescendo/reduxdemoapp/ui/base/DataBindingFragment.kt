package com.trycrescendo.reduxdemoapp.ui.base

import android.databinding.DataBindingUtil
import android.databinding.ViewDataBinding
import android.os.Bundle
import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * Created by eric on 9/13/17.
 */
abstract class DataBindingFragment<DB : ViewDataBinding> constructor(@LayoutRes val layout: Int = 0) : BaseFragment() {


    protected lateinit var binding: DB

    @LayoutRes
    fun layout(): Int = layout

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? = DataBindingUtil.inflate<DB>(inflater, layout(), container, false)
            .also {
                binding = it
            }.root
}