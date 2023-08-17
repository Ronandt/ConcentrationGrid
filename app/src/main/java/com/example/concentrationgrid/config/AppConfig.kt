package com.example.concentrationgrid.config


object AppConfig {

    internal class DefaultGridSettings private constructor(val shuffling: Boolean = false, val shufflingRateInSeconds: Int = 10) {
      companion object {
          fun getInstance(): DefaultGridSettings {
              return DefaultGridSettings()
          }
      }

    }
    internal fun getDefaultGridSettings(): DefaultGridSettings {
        return DefaultGridSettings.getInstance()
    }
}