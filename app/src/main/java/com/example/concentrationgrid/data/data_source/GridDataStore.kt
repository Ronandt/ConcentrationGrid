package com.example.concentrationgrid.data.data_source
import androidx.datastore.core.CorruptionException
import androidx.datastore.core.Serializer
import com.example.concentrationgrid.GridSettings
import com.example.concentrationgrid.config.AppConfig
import com.google.protobuf.InvalidProtocolBufferException
import java.io.InputStream
import java.io.OutputStream


object GridSettingsSerializer: Serializer<GridSettings> {
    override val defaultValue: GridSettings = GridSettings
        .getDefaultInstance().toBuilder()
        .setShufflingEnabled(AppConfig.getDefaultGridSettings().shuffling)
        .setShufflingRate(AppConfig.getDefaultGridSettings().shufflingRateInSeconds)
        .build()


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




//Backing field cannot be initialised but only the getter and setter property that modifies the backing field is allowed.
//A backing field iis a hidden field where the class actually stores the value. This is inefficient so therefore it is not allwoed