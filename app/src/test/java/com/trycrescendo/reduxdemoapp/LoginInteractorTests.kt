package com.trycrescendo.reduxdemoapp

import android.arch.persistence.room.EmptyResultSetException
import com.nhaarman.mockito_kotlin.MockitoKotlin
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.whenever
import com.trycrescendo.reduxdemoapp.data.Credentials
import com.trycrescendo.reduxdemoapp.data.CredentialsRepository
import com.trycrescendo.reduxdemoapp.login.LoginActions
import com.trycrescendo.reduxdemoapp.login.LoginInteractor
import com.trycrescendo.reduxdemoapp.rx.redux.Dispatcher
import io.reactivex.Flowable
import io.reactivex.Single
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.*
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnit
import org.mockito.stubbing.Answer
import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.schedulers.Schedulers
import io.reactivex.Scheduler





/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * See [testing documentation](http://d.android.com/tools/testing).
 */
class LoginInteractorTests {

    interface TestDispatcher: Dispatcher<LoginActions>

    val schedulerProviders: TestSchedulersProvider = TestSchedulersProvider()

    @Test
    fun loginActionSucceeds() {
        // Setup data
        val payloadObj = LoginActions.LoginAction("test@mock.com", "1111")
        val credentials = Credentials(email = "test@mock.com", accessToken = "12345", pass = "1111")
        val expectedObj = LoginActions.LoginActionSuccess("12345", "test@mock.com", "1111")
        var answerObj: LoginActions? = null // Comparing the answer object after

        // Create Mocks
        val credentialsRepository = Mockito.mock(CredentialsRepository::class.java)
        val dispatcher = Mockito.mock(TestDispatcher::class.java)

        `when`(credentialsRepository.findCredentials("test@mock.com", "1111")).thenReturn(Single.just(credentials))
        val answer: Answer<Unit> = Answer { invocationOnMock ->
            val flowable = invocationOnMock.getArgument<Flowable<LoginActions>>(0)
            answerObj = flowable.blockingFirst()
        }
        doAnswer(answer).`when`(dispatcher).dispatch(any())

        // Perform Test
        val loginInteractor = LoginInteractor(dispatcher, credentialsRepository, schedulerProviders)
        loginInteractor.actionMapper.accept(payloadObj)

        // Validate
        verify(dispatcher).dispatch(any())
        assertEquals(answerObj, expectedObj)
    }

    @Test
    fun loginActionFails() {

        val error = EmptyResultSetException("Query returned empty result set: ")
        val payloadObj = LoginActions.LoginAction("test@mock.com", "1111")
        val expectedObj = LoginActions.LoginActionFailure(error, "test@mock.com", "1111")
        var answerObj: LoginActions? = null

        val credentialsRepository = Mockito.mock(CredentialsRepository::class.java)
        val dispatcher = Mockito.mock(TestDispatcher::class.java)

        doReturn(Single.error<Throwable>(error)).`when`(credentialsRepository).findCredentials("test@mock.com", "1111")
        val answer: Answer<Unit> = Answer { invocationOnMock ->
            val flowable = invocationOnMock.getArgument<Flowable<LoginActions>>(0)
            answerObj = flowable.blockingFirst()
        }
        doAnswer(answer).`when`(dispatcher).dispatch(any())

        val loginInteractor = LoginInteractor(dispatcher, credentialsRepository, schedulerProviders)
        loginInteractor.actionMapper.accept(payloadObj)

        verify(dispatcher).dispatch(any())
        assertEquals(expectedObj, answerObj)
    }

    @Test
    fun registerActionSucceeds() {
        // Setup data
        val payloadObj = LoginActions.RegisterAction("test@mock.com", "1111")
        val credentials = Credentials(email = "test@mock.com", accessToken = "12345", pass = "1111")
        val expectedObj = LoginActions.RegisterActionSuccess("test@mock.com", "1111")
        var answerObj: LoginActions? = null // Comparing the answer object after

        // Create Mocks
        val credentialsRepository = Mockito.mock(CredentialsRepository::class.java)
        val dispatcher = Mockito.mock(TestDispatcher::class.java)

        `when`(credentialsRepository.saveCredentials("test@mock.com", "1111")).thenReturn(Single.just(true))
        val answer: Answer<Unit> = Answer { invocationOnMock ->
            val flowable = invocationOnMock.getArgument<Flowable<LoginActions>>(0)
            answerObj = flowable.blockingFirst()
        }
        doAnswer(answer).`when`(dispatcher).dispatch(any())

        // Perform Test
        val loginInteractor = LoginInteractor(dispatcher, credentialsRepository, schedulerProviders)
        loginInteractor.actionMapper.accept(payloadObj)

        // Validate
        verify(dispatcher).dispatch(any())
        assertEquals(answerObj, expectedObj)
    }

    @Test
    fun registerActionFails() {

        val error = EmptyResultSetException("Couldn't persist action")
        val payloadObj = LoginActions.RegisterAction("test@mock.com", "1111")
        val expectedObj = LoginActions.RegisterActionFailure(error, "test@mock.com", "1111")
        var answerObj: LoginActions? = null

        val credentialsRepository = Mockito.mock(CredentialsRepository::class.java)
        val dispatcher = Mockito.mock(TestDispatcher::class.java)

        doReturn(Single.error<Throwable>(error)).`when`(credentialsRepository).saveCredentials("test@mock.com", "1111")
        val answer: Answer<Unit> = Answer { invocationOnMock ->
            val flowable = invocationOnMock.getArgument<Flowable<LoginActions>>(0)
            answerObj = flowable.blockingFirst()
        }
        doAnswer(answer).`when`(dispatcher).dispatch(any())

        val loginInteractor = LoginInteractor(dispatcher, credentialsRepository, schedulerProviders)
        loginInteractor.actionMapper.accept(payloadObj)

        verify(dispatcher).dispatch(any())
        assertEquals(expectedObj, answerObj)
    }
}