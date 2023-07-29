package com.example.concentrationgrid.concentration_grid.screens
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.example.concentrationgrid.util.PreferenceResolver

@Composable
fun SettingScreen() {
    val gridSettingsPreferences = PreferenceResolver(LocalContext.current).obtainGridSettings()
    gridSettingsPreferences.getString("ShuffleRate", "0")

}