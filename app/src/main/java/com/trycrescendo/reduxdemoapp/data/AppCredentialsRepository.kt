package com.trycrescendo.reduxdemoapp.data

import io.reactivex.Flowable
import io.reactivex.FlowableTransformer
import io.reactivex.Single
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.reactivestreams.Publisher
import javax.inject.Inject

/**
 * Created by stephen on 10/23/17.
 */
class AppCredentialsRepository @Inject constructor(private val appDatabase: AppDatabase): CredentialsRepository {

    override fun findCredentials(email: String, pass: String): Single<Credentials> {
        val credentials: Credentials = Credentials(email = email, pass = pass, accessToken = "")
        return try {
            appDatabase.credentialsDao().credentials()
                    .subscribeOn(Schedulers.io())
                    .onErrorReturnItem(credentials)
                    .compose(CredentialsAuth(email, pass))
        } catch (t: Throwable) {
            Single.error<Credentials>(Error("Invalid email or password"))
        }
    }

    override fun saveCredentials(email: String, pass: String): Single<Boolean> {
        return Flowable.just(appDatabase.credentialsDao().createCredentials(Credentials(email = email, pass = pass, accessToken = null)))
                .singleOrError()
                .map { it != -1L }
    }

    inner class CredentialsAuth(private val correctEmail: String, private val correctPass: String):
            SingleTransformer<Credentials, Credentials> {

        override fun apply(credentials: Single<Credentials>): Single<Credentials> {
            val it = credentials.blockingGet()
            return if (it.email == correctEmail && it.pass == correctPass) {
                appDatabase.credentialsDao().updateToken("accessToken123")
                appDatabase.credentialsDao().credentials()
            } else {
                Single.error<Credentials>(Error("Invalid email or password"))
            }

        }

    }

}