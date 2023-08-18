package com.example.concentrationgrid.presentation.concentration_grid.states

import com.example.concentrationgrid.config.AppConfig

data class SettingsUiState(
    val shufflingEnabled: Boolean = AppConfig.getDefaultGridSettings().shufflingEnabled,
    val shufflingRateInSeconds: Int = AppConfig.getDefaultGridSettings().shufflingRateInSeconds
)
