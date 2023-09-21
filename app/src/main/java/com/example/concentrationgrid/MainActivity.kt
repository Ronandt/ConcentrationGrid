package com.example.concentrationgrid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.concentrationgrid.presentation.concentration_grid.ConcentrationViewModel
import com.example.concentrationgrid.presentation.concentration_grid.ConcentrationGridScreen
import com.example.concentrationgrid.presentation.grid_settings.GridSettingsEvent
import com.example.concentrationgrid.presentation.grid_settings.GridSettingsViewModel
import com.example.concentrationgrid.presentation.grid_settings.SettingScreen
import com.example.concentrationgrid.presentation.util.ScreenRoutes
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)



        setContent {
            val navControllerState = rememberNavController()
            val scope = rememberCoroutineScope()
            Scaffold {
                Box(modifier = Modifier.padding(it)) {
                    NavHost(navController = navControllerState, startDestination = ScreenRoutes.ConcentrationGridScreen) {
                        composable( ScreenRoutes.ConcentrationGridScreen) {
                            val concentrationViewModel by remember { viewModels<ConcentrationViewModel>() }
                            ConcentrationGridScreen(concentrationViewModel) {
                                navControllerState.navigate(
                                    ScreenRoutes.SettingScreen
                                ) {
                                    launchSingleTop = true
                                }
                            }
                        }
                        composable( ScreenRoutes.SettingScreen, enterTransition = {
                            slideInHorizontally()
                        }, exitTransition = { slideOutHorizontally(animationSpec = tween(durationMillis = 100, delayMillis = 20))}) {
                            val gridSettingsViewModel by remember { viewModels<GridSettingsViewModel>() }
                            val toggleShuffling = { gridSettingsEvent: GridSettingsEvent ->
                                scope.launch {
                                    gridSettingsViewModel.onEvent(gridSettingsEvent)
                                }
                            }

                            SettingScreen(uiState = gridSettingsViewModel.gridSettingsUiState, navigateBack = {navControllerState.navigateUp()},
                                enableShufflingSettings = {toggleShuffling(GridSettingsEvent.ToggleShufflingSettings(true))},
                                disableShufflingSettings  = { toggleShuffling(GridSettingsEvent.ToggleShufflingSettings(false))}, scope = scope)
                        }
                    }
                }
            }


        }
    }
}


@Preview(showBackground = true)
@Composable
fun GreetingPreview() {

}