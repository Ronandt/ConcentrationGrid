package com.example.concentrationgrid.data.data_source

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.Serializer


class GridDataStore(private var context: Context) {
    fun obtainGridSettings(): SharedPreferences {
        val gridSettingPreferenceName = "GridSettings"
        return context.getSharedPreferences(gridSettingPreferenceName, Context.MODE_PRIVATE)
    }
}

/*object GridSettingsSerializer: Serializer<GridSettings>() {
    override val defaultValue: GridSettings
        get() = TODO("Not yet implemented")
}*/