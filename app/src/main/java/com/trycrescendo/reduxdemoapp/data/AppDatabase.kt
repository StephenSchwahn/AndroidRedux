package com.trycrescendo.reduxdemoapp.data

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context

/**
 * Created by stephen on 10/23/17.
 */
@Database(entities = arrayOf(Credentials::class), version = 1)
abstract class AppDatabase : RoomDatabase() {

    abstract fun credentialsDao(): CredentialsDao

    companion object {
        val NAME = "App.db"
        var INSTANCE: AppDatabase? = null

        private val sLock = Any()

        fun instance(context: Context): AppDatabase {
            synchronized(sLock) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.applicationContext,
                            AppDatabase::class.java, NAME)
                            .build()
                }
                return INSTANCE!!
            }
        }

        //        @VisibleForTesting
        fun inMemoryInstance(context: Context): AppDatabase {
            INSTANCE = Room.inMemoryDatabaseBuilder(context.applicationContext,
                    AppDatabase::class.java).build()
            return INSTANCE!!
        }
    }

}