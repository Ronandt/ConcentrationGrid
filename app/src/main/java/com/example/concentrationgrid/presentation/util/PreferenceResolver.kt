package com.example.concentrationgrid.presentation.util

import android.content.Context
import android.content.SharedPreferences

class PreferenceResolver(private var context: Context) {
    fun obtainGridSettings(): SharedPreferences {
        val gridSettingPreferenceName = "GridSettings"
        return context.getSharedPreferences(gridSettingPreferenceName, Context.MODE_PRIVATE)
    }
}

