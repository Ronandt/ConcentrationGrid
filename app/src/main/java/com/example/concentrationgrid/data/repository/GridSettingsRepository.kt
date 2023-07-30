package com.example.concentrationgrid.data.repository

import android.content.Context
import com.example.concentrationgrid.GridSettings
import com.example.concentrationgrid.data.data_source.gridSettings
import kotlinx.coroutines.flow.Flow


class GridSettingsRepository(private val context: Context) {

    fun obtainGridSettings(): Flow<GridSettings> {
        return context.gridSettings.data
    }

     suspend fun configureGridSettings(gridSettings: GridSettings) {
        context.gridSettings.updateData {
            currentSettings ->
            currentSettings.toBuilder().setShufflingEnabled(gridSettings.shufflingEnabled)
                .setShufflingRate(gridSettings.shufflingRate).build()
        }
    }
}