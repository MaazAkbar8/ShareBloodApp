package com.example.shareblood.Adapters

import android.content.Context
import android.content.SharedPreferences

class SharedPreferencesHelper(context: Context) {
    private val preferences: SharedPreferences =
        context.getSharedPreferences("MyPrefs", Context.MODE_PRIVATE)

    fun saveSelectedOption(option: String) {
        preferences.edit().putString("selectedOption", option).apply()
    }

    fun getSelectedOption(): String {
        return preferences.getString("selectedOption", "") ?: ""
    }
}