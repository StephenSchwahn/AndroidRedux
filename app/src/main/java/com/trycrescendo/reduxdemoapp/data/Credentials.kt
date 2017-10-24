package com.trycrescendo.reduxdemoapp.data

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by stephen on 10/23/17.
 */
@Entity(tableName = "Credentials")
data class Credentials(
        @PrimaryKey(autoGenerate = false) var id: Long = 0,
        public var email: String?,
        public var accessToken: String?,
        public var pass: String?
) {
    fun isAuthorized(): Boolean {
        if (email!!.isNotEmpty() && accessToken!!.isNotEmpty() && pass!!.isNotEmpty()) {
            return true
        }
        return false
    }
}