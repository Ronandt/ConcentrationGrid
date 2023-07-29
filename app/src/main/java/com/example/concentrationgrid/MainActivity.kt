package com.example.concentrationgrid

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.concentrationgrid.concentration_grid.ConcentrationViewModel
import com.example.concentrationgrid.concentration_grid.screens.ConcentrationGridScreen
import com.example.concentrationgrid.concentration_grid.screens.SettingScreen
import com.example.concentrationgrid.util.ScreenRoutes


class MainActivity : ComponentActivity() {


    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val concentrationViewModel by viewModels<ConcentrationViewModel>()

        setContent {
            val navControllerState = rememberNavController()
            Scaffold() {
                Box(modifier = Modifier.padding(it)) {
                    NavHost(navController = navControllerState, startDestination = ScreenRoutes.ConcentrationGridScreen) {
                        composable( ScreenRoutes.ConcentrationGridScreen) {
                            ConcentrationGridScreen(concentrationViewModel, settingsNavigation = {navControllerState.navigate(
                                ScreenRoutes.SettingScreen) {
                                launchSingleTop = true
                            } })
                        }
                        composable( ScreenRoutes.SettingScreen) {
                            SettingScreen()
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