package com.example.concentrationgrid.presentation.grid_settings
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.concentrationgrid.presentation.grid_settings.components.SelectionButton
import com.example.concentrationgrid.presentation.grid_settings.states.GridSettingsUiState
import com.example.concentrationgrid.presentation.grid_settings.states.ScreenState
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SettingScreen(
    uiState: StateFlow<GridSettingsUiState>,
    enableShufflingSettings: () -> Unit,
    disableShufflingSettings: () -> Unit,
    navigateBack: () -> Unit,
    scope: CoroutineScope
) {
    val state = uiState.collectAsStateWithLifecycle().value
    Scaffold(topBar = {
        TopAppBar(colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.White), navigationIcon = {
            IconButton(onClick = navigateBack) {
                Icon(Icons.Default.ArrowBack, contentDescription = "Navigate back", tint = Color.Black, modifier = Modifier.size(34.dp))
            }
        }, title = { Text(text = "Settings")})
    }) {
        Box(modifier = Modifier.padding(it)) {
            Column(modifier= Modifier
                .fillMaxSize()
                .padding(24.dp)) {
                Row(horizontalArrangement = Arrangement.SpaceBetween, modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
                    Text(text = "Shuffling: ", color = Color.DarkGray, fontWeight = FontWeight.SemiBold, fontSize = 22.sp)
                    Row(horizontalArrangement = Arrangement.spacedBy(10.dp)) {

                        SelectionButton(
                            title = "Enabled",
                            onClick = {
                                scope.launch {
                                   enableShufflingSettings()
                                }
                                },
                            selected = state.screenState == ScreenState.Ready
                                    && state.shufflingEnabled,
                            )
                        SelectionButton(
                            title = "Disabled",
                            onClick = {
                                scope.launch {
                                   disableShufflingSettings()
                                }
                               },
                            selected = state.screenState == ScreenState.Ready
                                    && !state.shufflingEnabled,

                        )
                    }

                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun GridSettingsPreview() {

}