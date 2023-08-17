package com.example.concentrationgrid.config


object AppConfig {

    internal class DefaultGridSettings
    private constructor(
        val shufflingEnabled: Boolean,
        val shufflingRateInSeconds: Int
    ) {
        companion object {
            fun getInstance(): DefaultGridSettings {
                return DefaultGridSettings(shufflingEnabled = false, shufflingRateInSeconds = 10)
            }
        }

    }

    internal fun getDefaultGridSettings(): DefaultGridSettings {
        return DefaultGridSettings.getInstance()
    }
}