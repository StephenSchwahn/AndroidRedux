package com.trycrescendo.reduxdemoapp.data

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers

/**
 * Created by stephen on 10/24/17.
 */
interface CredentialsRepository {
    fun findCredentials(email: String, pass: String): Single<Credentials>
    fun saveCredentials(email: String, pass: String): Single<Boolean>
}