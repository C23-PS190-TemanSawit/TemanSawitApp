package com.example.temansawit.di

import android.content.Context
import android.content.SharedPreferences

object Preferences {
    fun initPref(context: Context, name: String): SharedPreferences {
        return context.getSharedPreferences(name, Context.MODE_PRIVATE)
    }

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

    fun saveAccessToken(accessToken: String, sharedPreferences: SharedPreferences) {
        val editor = sharedPreferences.edit()
        editor.putString("access_token", accessToken)
        editor.apply()
    }
    fun saveRefreshToken(refreshToken: String, sharedPreferences: SharedPreferences) {
        val editor = sharedPreferences.edit()
        editor.putString("refresh_token", refreshToken)
        editor.apply()
    }


    fun getRefreshToken(sharedPreferences: SharedPreferences): String {
        return sharedPreferences.getString("refresh_token", null).toString()
    }
    fun getAccessToken(sharedPreferences: SharedPreferences): String {
        return sharedPreferences.getString("access_token", null).toString()
    }

    fun newAccessToken(accessToken: String, sharedPreferences: SharedPreferences) {
        val editor = sharedPreferences.edit()
        editor.putString("access_token", accessToken)
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