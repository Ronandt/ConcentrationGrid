package com.example.concentrationgrid.presentation.util
/**
 * This singleton defines the routes for various screens in the Compose-based navigation system.
 * These routes are used to navigate between different screens within the app.
 * @property [ScreenRoutes.ConcentrationGridScreen] links to [com.example.concentrationgrid.presentation.concentration_grid.ConcentrationGridScreen]
 * @property [ScreenRoutes.SettingScreen] links to [com.example.concentrationgrid.presentation.grid_settings.SettingScreen]
 */
object ScreenRoutes {
    const val ConcentrationGridScreen = "concentrationGrid"
    const val SettingScreen = "setting"
}