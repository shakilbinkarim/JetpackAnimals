package io.github.shakilbinkarim.jetpackanimals.utils

import android.content.Context
import android.preference.PreferenceManager

class SharedPreferencesUtil(context: Context) {

    private val API_KEY = "api key"

    private val prefs = PreferenceManager.getDefaultSharedPreferences(context.applicationContext)

    fun saveApiKey(key: String?) = prefs.edit().putString(API_KEY, key).apply()

    fun getApiKey() = prefs.getString(API_KEY, null)

}