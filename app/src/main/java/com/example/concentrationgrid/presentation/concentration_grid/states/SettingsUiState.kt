package com.example.concentrationgrid.presentation.concentration_grid.states

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.concentrationgrid.config.AppConfig
@Immutable
data class SettingsUiState(
    val shufflingEnabled: Boolean = AppConfig.getDefaultGridSettings().shufflingEnabled,
    val shufflingRateInSeconds: Int = AppConfig.getDefaultGridSettings().shufflingRateInSeconds
)
