package com.example.concentrationgrid.presentation.grid_settings

sealed class GridSettingsEvent {
    data class ToggleShufflingSettings(val enableShuffling: Boolean): GridSettingsEvent()
}