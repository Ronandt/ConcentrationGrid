package com.example.concentrationgrid.presentation.grid_settings.states

import androidx.compose.runtime.Immutable
import com.example.concentrationgrid.config.AppConfig

@Immutable
data class GridSettingsUiState(
    val shufflingEnabled: Boolean = AppConfig.getDefaultGridSettings().shufflingEnabled,
    val shuffleRateInSeconds: Int = AppConfig.getDefaultGridSettings().shufflingRateInSeconds,
    val screenState: ScreenState
)