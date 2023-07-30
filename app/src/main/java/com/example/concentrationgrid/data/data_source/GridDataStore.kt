package com.example.concentrationgrid.data.data_source

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import com.example.concentrationgrid.GridSettings
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream

object GridSettingsSerializer: Serializer<GridSettings> {
    override val defaultValue: GridSettings = GridSettings.getDefaultInstance()

    override suspend fun readFrom(input: InputStream): GridSettings {
        try {
            return GridSettings.parseFrom(input)

        } catch(exception: InvalidProtocolBufferException) {
            throw CorruptionException("Cannot read from GridSettings ProtoDataStore: ", exception)
        }
    }

    override suspend fun writeTo(t: GridSettings, output: OutputStream) {
        t.writeTo(output)
    }

}

val Context.gridSettings: DataStore<GridSettings>  by dataStore(
    fileName = "grid_settings.pb",
    serializer = GridSettingsSerializer
)