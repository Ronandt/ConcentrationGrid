package com.example.concentrationgrid.data.data_source

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.DataStore
import androidx.datastore.core.Serializer
import androidx.datastore.dataStore
import androidx.datastore.dataStoreFile
import com.example.concentrationgrid.GridSettings
import com.google.protobuf.InvalidProtocolBufferException
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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




//Backing field cannot be initalisded but only the getter and setter property that modifies the backing field is allowed.
//A backing field iis a hidden field where the class actually stores the value. This is inefficient so therefore it is not allwoed