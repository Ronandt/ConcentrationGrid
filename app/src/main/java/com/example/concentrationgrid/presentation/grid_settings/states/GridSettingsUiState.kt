package com.example.concentrationgrid.presentation.grid_settings.states

import com.example.concentrationgrid.config.AppConfig

data class GridSettingsUiState(
    val shuffling: Boolean = AppConfig.getDefaultGridSettings().shuffling,
    val shuffleRateInSeconds: Int = AppConfig.getDefaultGridSettings().shufflingRateInSeconds,
    val screenState: ScreenState
)