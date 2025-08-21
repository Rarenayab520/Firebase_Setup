package com.codingempire.firebase_setup

import android.content.Context
import android.content.SharedPreferences

class SharedPrefs(context: Context) {

    private val PREF_NAME = "auth"
    private val KEY_ID = "id"
    private val KEY_NAME = "name"
    private val KEY_EMAIL = "email"
    private val KEY_PASSWORD = "password"
    private val CREATED_AT = "createdAt"

    private val sp: SharedPreferences =
        context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)

    fun saveUser(user: User) {

        sp.edit().putString(KEY_ID, user.id)
            .putString(KEY_NAME, user.name)
            .putString(KEY_EMAIL, user.email)
            .putString(KEY_PASSWORD, user.password)
            .putLong(CREATED_AT, user.createdAt)
            .apply()
    }

    fun getUser(): User? {

        val id = sp.getString(KEY_ID, null) ?: return null
        val name = sp.getString(KEY_NAME, "") ?: ""
        val email = sp.getString(KEY_EMAIL, "") ?: ""
        val password = sp.getString(KEY_PASSWORD, "") ?: ""
        val createdAt = sp.getLong(CREATED_AT, 0L) ?: 0L

        return User(id, name, email, password, createdAt)

    }

    fun clearUser() {
        sp.edit().clear().apply()
    }

}
