package com.sopt.now.compose.util

import android.content.Context
import android.content.SharedPreferences

object PreferencesUtil {
    private const val PREFS_NAME = "prefs"
    private const val USER_ID = "userId"

    private fun getPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)
    }

    fun saveUserId(context: Context, userId: String) {
        getPreferences(context).edit().putString(USER_ID, userId).apply()
    }

    fun getUserId(context: Context): String? {
        return getPreferences(context).getString(USER_ID, null)
    }

    fun clearUserId(context: Context) {
        getPreferences(context).edit().remove(USER_ID).apply()
    }
}