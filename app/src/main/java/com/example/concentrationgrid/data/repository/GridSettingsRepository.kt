package com.example.concentrationgrid.data.repository

import androidx.datastore.core.DataStore
import com.example.concentrationgrid.GridSettings
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GridSettingsRepository @Inject constructor(private val gridSettingsDataStore: DataStore<GridSettings>){

    fun obtainGridSettings(): Flow<GridSettings> {
        return gridSettingsDataStore.data

    }

     suspend fun configureGridSettings(gridSettings: GridSettings) {
        gridSettingsDataStore.updateData {
            currentSettings ->
            currentSettings.toBuilder().setShufflingEnabled(gridSettings.shufflingEnabled)
                .setShufflingRate(gridSettings.shufflingRate).build()
        }
    }
}