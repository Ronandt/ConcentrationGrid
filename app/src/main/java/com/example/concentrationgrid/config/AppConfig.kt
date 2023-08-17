package com.example.concentrationgrid.config


object AppConfig {
    data class DefaultGridSettings(val shuffling: Boolean = false, val shufflingRateInSeconds: Int = 10)
    fun getDefaultGridSettings(): DefaultGridSettings {
        return DefaultGridSettings()
    }
}