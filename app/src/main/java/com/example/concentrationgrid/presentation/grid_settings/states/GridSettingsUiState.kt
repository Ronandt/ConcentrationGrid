package com.example.concentrationgrid.presentation.grid_settings.states

data class GridSettingsUiState(
    val shuffling: Boolean = false,
    val shuffleRateInSeconds: Int = 10,
    val screenState: ScreenState
)