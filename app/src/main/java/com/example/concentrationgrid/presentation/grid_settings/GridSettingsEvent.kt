package com.example.concentrationgrid.presentation.grid_settings

sealed class GridSettingsEvent {
    data class ToggleShuffling(val enabled: Boolean): GridSettingsEvent()
}