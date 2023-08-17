package com.example.concentrationgrid.data.repository

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.concentrationgrid.GridSettings
import com.example.concentrationgrid.data.data_source.DataStoreModule
import dagger.hilt.android.qualifiers.ApplicationContext
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