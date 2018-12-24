package com.devgeek.footballmatchschedule.sharedpreferences

import android.content.Context
import android.content.SharedPreferences

class Preferences (context: Context) {
    val PREFS_NAME = "com.devgeek.footballmatchschedule"
    val TO_SEARCH = "to_search"
    val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, 0)

    var toSearch: String
        get() = prefs.getString(TO_SEARCH, "Match")
        set(value) = prefs.edit().putString(TO_SEARCH, value).apply()
}