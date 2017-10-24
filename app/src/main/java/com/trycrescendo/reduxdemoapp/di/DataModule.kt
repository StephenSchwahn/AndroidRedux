package com.trycrescendo.reduxdemoapp.di

import android.content.SharedPreferences
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import com.trycrescendo.reduxdemoapp.data.AppCredentialsRepository
import com.trycrescendo.reduxdemoapp.data.AppDatabase
import com.trycrescendo.reduxdemoapp.data.CredentialsRepository
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.*
import javax.inject.Named
import javax.inject.Singleton

/**
 * Created by eric on 9/4/17.
 */
@Module
class DataModule {

    @Singleton
    @Provides
    fun provideDatabase(application: App): AppDatabase = AppDatabase.instance(application)

    @Singleton
    @Provides
    fun provideCredentialStorage(appDatabase: AppDatabase) = appDatabase.credentialsDao()

    @Singleton
    @Provides
    fun provideCredentialsRepo(appDatabase: AppDatabase): CredentialsRepository = AppCredentialsRepository(appDatabase)

}