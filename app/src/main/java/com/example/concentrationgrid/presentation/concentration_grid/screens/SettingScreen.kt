package com.example.concentrationgrid.presentation.concentration_grid.screens
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.concentrationgrid.presentation.util.PreferenceResolver

@Composable
fun SettingScreen() {
    val gridSettingsPreferences = PreferenceResolver(LocalContext.current).obtainGridSettings()
    val shuffleRate = gridSettingsPreferences.getString("ShuffleRate", "0")


}