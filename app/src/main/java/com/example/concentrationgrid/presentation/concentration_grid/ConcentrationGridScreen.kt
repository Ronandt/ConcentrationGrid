package com.example.concentrationgrid.presentation.concentration_grid


import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.concentrationgrid.presentation.concentration_grid.components.DefaultButton
import com.example.concentrationgrid.presentation.concentration_grid.components.GameEndDialog
import com.example.concentrationgrid.presentation.concentration_grid.components.GridCell
import com.example.concentrationgrid.presentation.concentration_grid.states.GameState
import com.example.concentrationgrid.presentation.concentration_grid.theme.BlackWithAlpha
import com.example.concentrationgrid.presentation.concentration_grid.theme.ConcentrationGridTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConcentrationGridScreen(
    concentrationViewModel: ConcentrationViewModel,
    settingsNavigation: () -> Unit = {}
) {

    ConcentrationGridTheme {
        val concentrationGridUiState =
            concentrationViewModel.concentrationGridState.collectAsStateWithLifecycle().value

        val scope = rememberCoroutineScope()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {


            Scaffold(topBar = {
                TopAppBar(
                    title = { Text("Concentration game", color = Color.White) },
                    colors = TopAppBarDefaults.smallTopAppBarColors(containerColor = Color.Black)
                )
            }, floatingActionButton = {
                FloatingActionButton(
                    onClick = settingsNavigation,
                    containerColor = Color.DarkGray
                ) {
                    Icon(
                        imageVector = Icons.Default.Settings,
                        contentDescription = "Grid Settings",
                        tint = Color.White
                    )
                }
            }, floatingActionButtonPosition = FabPosition.End) { it ->

                BoxWithConstraints(modifier = Modifier.padding(it)) {
                    /*
                    Minor optimisation where if the screen size changes it, it won't change if it's the same orientation as before
                    */
                    val orientation by remember {
                        derivedStateOf {
                        if(maxHeight > maxWidth) Orientation.Vertical else Orientation.Horizontal
                    }}
                    Column(modifier = Modifier) {
                        Box {
                            LazyVerticalGrid(
                                columns = if (orientation == Orientation.Vertical) GridCells.Fixed(10) else GridCells.Fixed(
                                    20
                                ),
                                modifier = Modifier.padding(8.dp)
                            ) {
                                items(100) {
                                    var onError by remember { mutableStateOf(false)}
                                    GridCell(value = concentrationGridUiState.gridNumberSequence[it].toString(),
                                        scored = concentrationGridUiState.gridNumberSequence[it] <= concentrationGridUiState.currentNumber,
                                        error = onError,
                                        clickable = concentrationGridUiState.gameState == GameState.Playing) {
                                        concentrationViewModel.onEvent(
                                            ConcentrationGridEvent.ClickedGridCell(it) {
                                                scope.launch {
                                                    onError = true
                                                    delay(500L)
                                                    onError = false
                                                }
                                            })
                                    }


                                }

                            }
                            if (concentrationGridUiState.gameState == GameState.NotStarted) {
                                Spacer(
                                    modifier = Modifier
                                        .matchParentSize()
                                        .background(BlackWithAlpha)
                                )
                                DefaultButton(text = "Start", onClick = {
                                    concentrationViewModel.onEvent(ConcentrationGridEvent.ResolveGameState(GameState.Playing))
                                }, modifier = Modifier.align(Alignment.Center))

                            }


                        }

                        Column(
                            modifier = Modifier
                                .fillMaxSize(),

                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Column(
                                verticalArrangement = Arrangement.Center,
                                horizontalAlignment = Alignment.CenterHorizontally,
                                modifier = Modifier
                                    .border(
                                        width = 3.dp,
                                        brush = Brush.linearGradient(
                                            listOf(
                                                MaterialTheme.colorScheme.outline,
                                                MaterialTheme.colorScheme.outline
                                            )
                                        ),
                                        shape = RoundedCornerShape(10.dp)
                                    )
                                    .padding(
                                        bottom = 16.dp,
                                        start = 16.dp,
                                        end = 16.dp,
                                        top = 10.dp
                                    )
                            ) {
                                Row(
                                    horizontalArrangement = Arrangement.SpaceEvenly,

                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = concentrationGridUiState.timeLeftInSeconds.floorDiv(
                                            60
                                        )
                                            .toString() + ":" + concentrationGridUiState.timeLeftInSeconds.mod(
                                            60
                                        ).toString().padStart(2, '0') + " —",
                                        fontSize = 20.sp,
                                        fontWeight = FontWeight.Bold
                                    )

                                    TextButton(
                                        onClick = {
                                            concentrationViewModel.onEvent(ConcentrationGridEvent.ResolveGameState(GameState.NotStarted))
                                        },
                                        enabled = concentrationGridUiState.gameState == GameState.Playing
                                    ) {
                                        Text(
                                            "Reset",
                                            fontSize = 18.sp,
                                            color = if (concentrationGridUiState.gameState == GameState.Playing) Color(
                                                0xFF000000
                                            ) else Color.DarkGray
                                        )
                                    }
                                }


                                Text(
                                    text = "Current Number: ${
                                        Integer.max(
                                            concentrationGridUiState.currentNumber,
                                            0
                                        )
                                    }",
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold,

                                    )
                            }

                        }


                    }

                    if (concentrationGridUiState.gameState == GameState.Won) {
                        GameEndDialog(title = "You won!",
                            description = "You won in ${
                                concentrationGridUiState.timeLeftInSeconds.floorDiv(
                                    60
                                )
                            }  minutes and ${
                                concentrationGridUiState.timeLeftInSeconds.mod(
                                    60
                                ).toString().padStart(2, '0')
                            } seconds",
                            onClick = {  concentrationViewModel.onEvent(ConcentrationGridEvent.ResolveGameState(GameState.NotStarted))})
                    }
                    else if ((concentrationGridUiState.gameState == GameState.Lost)) {
                        GameEndDialog(title = "Game finished",
                            description = "Your total score is: ${concentrationGridUiState.currentNumber + 1}",
                            onClick = {  concentrationViewModel.onEvent(ConcentrationGridEvent.ResolveGameState(GameState.NotStarted))})
                    }


                }
            }


        }

    }
}