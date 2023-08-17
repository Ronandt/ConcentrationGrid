package com.example.concentrationgrid.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.dataStore
import com.example.concentrationgrid.GridSettings
import com.example.concentrationgrid.data.data_source.GridSettingsSerializer
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    private val Context.gridSettings: DataStore<GridSettings> by dataStore(
        fileName = "grid_settings.pb",
        serializer = GridSettingsSerializer
    )

    @Provides
    fun provideGridSettingsDataStore(@ApplicationContext context: Context) : DataStore<GridSettings> {
        return context.gridSettings

    }
}