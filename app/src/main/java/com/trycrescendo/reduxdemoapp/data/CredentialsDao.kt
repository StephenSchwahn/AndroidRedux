package com.trycrescendo.reduxdemoapp.data

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Created by stephen on 10/23/17.
 */
@Dao
interface CredentialsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun createCredentials(credentials: Credentials): Long

    @Query("SELECT * FROM Credentials WHERE id = 1")
    fun credentials(): Single<Credentials>

    @Query("UPDATE Credentials SET email = :email WHERE id = 1")
    fun updateEmail(email: String?)

    @Query("UPDATE Credentials SET accessToken = :accessToken WHERE id = 1")
    fun updateToken(accessToken: String?)

    @Query("DELETE FROM Credentials")
    fun deleteCredentials(): Int

}