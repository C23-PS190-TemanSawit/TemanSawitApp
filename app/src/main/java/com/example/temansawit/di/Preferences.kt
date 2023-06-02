package com.example.temansawit.di

import android.content.SharedPreferences

object Preferences {
    private const val KEY_IS_ONBOARDED = "is_onboarded"
    private const val KEY_IS_LOGGED_IN = "is_logged_in"

    fun isOnboarded(sharedPreferences: SharedPreferences): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_ONBOARDED, false)
    }
    fun setOnboarded(sharedPreferences: SharedPreferences, isOnboarded: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_IS_ONBOARDED, isOnboarded)
        editor.apply()
    }

    fun isLoggedIn(sharedPreferences: SharedPreferences): Boolean {
        return sharedPreferences.getBoolean(KEY_IS_LOGGED_IN, false)
    }
    fun setLoggedIn(sharedPreferences: SharedPreferences, isLoggedIn: Boolean) {
        val editor = sharedPreferences.edit()
        editor.putBoolean(KEY_IS_LOGGED_IN, isLoggedIn)
        editor.apply()
    }
}