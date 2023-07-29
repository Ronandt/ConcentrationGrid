package com.example.concentrationgrid.concentration_grid.screens

import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.concentrationgrid.concentration_grid.ConcentrationViewModel
import com.example.concentrationgrid.concentration_grid.states.GameState
import com.example.concentrationgrid.ui.theme.ConcentrationGridTheme
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConcentrationGridScreen(concentrationViewModel: ConcentrationViewModel, settingsNavigation: () -> Unit = {}) {
    ConcentrationGridTheme {


        val concentrationGridUiState =
            concentrationViewModel.concentrationGridState.collectAsState().value
        val scope = rememberCoroutineScope()
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = Color.White
        ) {

            LaunchedEffect(concentrationGridUiState.gameState) {
                while (true) {
                    if (concentrationGridUiState.gameState == GameState.Idle) concentrationViewModel.shuffleGridSequence()
                    delay(100)
                }

            }

            Scaffold(topBar = {
                TopAppBar(
                    title = { Text("Concentration game", color = Color.White) },
                    colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = MaterialTheme.colorScheme.primary)
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
                    val orientation = this.maxHeight > this.maxWidth
                    Column() {
                        Box(modifier = Modifier.background(Color.Black)) {
                            LazyVerticalGrid(
                                columns = if (orientation) GridCells.Fixed(10) else GridCells.Fixed(
                                    20
                                ),
                                modifier = if (concentrationGridUiState.gameState == GameState.Idle) Modifier.background(
                                    Color.Black
                                ) else Modifier
                            ) {
                                items(100) {
                                    //var cellState by remember {mutableStateOf(GridCellState.Default)}
                                    var onError by remember { mutableStateOf(false) }
                                    val transition =
                                        updateTransition(
                                            targetState = onError,
                                            "Error state"
                                        )
                                    val colour =
                                        if (concentrationGridUiState.gridNumberSequence[it] <= concentrationGridUiState.currentNumber) Color.Green else Color.White
                                    val backgroundColourTrans by transition.animateColor(
                                        label = "border color"
                                    ) { isSelected ->
                                        if (isSelected) Color.Red else colour
                                    }
                                    Text(concentrationGridUiState.gridNumberSequence[it].toString()
                                        .padStart(2, '0'),
                                        color = Color.Black,
                                        modifier = Modifier
                                            .background(if (!onError) colour else backgroundColourTrans)
                                            .clickable {
                                                if ((concentrationGridUiState.currentNumber + 1) == concentrationGridUiState.gridNumberSequence[it]) {
                                                    concentrationViewModel.updateCurrentScore()
                                                    if (concentrationGridUiState.currentNumber >= 99) concentrationViewModel.resolveGameState(
                                                        GameState.Won
                                                    )
                                                } else {
                                                    if(concentrationGridUiState.gameState == GameState.Playing) {
                                                        scope.launch {
                                                            onError = true
                                                            delay(500L)
                                                            onError = false
                                                        }
                                                    }


                                                }

                                            }
                                            .padding(10.dp, 10.dp),
                                        textAlign = TextAlign.Center,
                                        softWrap = false,
                                        overflow = TextOverflow.Visible,
                                        maxLines = 1)

                                }

                            }
                            if (concentrationGridUiState.gameState == GameState.Idle) {
                                Spacer(
                                    modifier = Modifier
                                        .matchParentSize()
                                        .background(Color(0xDD000000))
                                )
                                Button(onClick = {
                                    concentrationViewModel.resolveGameState(
                                        GameState.Playing
                                    )
                                }, modifier = Modifier.align(Alignment.Center)) {
                                    Text("Start")
                                }
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
                                                Color.Black,
                                                Color.Black
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
                                            concentrationViewModel.resolveGameState(
                                                GameState.Idle
                                            )
                                        },
                                        enabled = concentrationGridUiState.gameState == GameState.Playing
                                    ) {
                                        Text("Reset", fontSize = 18.sp)
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
                        AlertDialog(onDismissRequest = {}, confirmButton = {
                            Button(onClick = {
                                concentrationViewModel.resolveGameState(GameState.Idle)
                            }) {
                                Text("Reset")
                            }

                        }, title = {
                            Text(
                                text = "You won in ${
                                    concentrationGridUiState.timeLeftInSeconds.floorDiv(
                                        60
                                    )
                                        .toString() + " minutes and " + concentrationGridUiState.timeLeftInSeconds.mod(
                                        60
                                    ).toString().padStart(2, '0')
                                } seconds"
                            )
                        })
                    }
                    if ((concentrationGridUiState.gameState == GameState.Lost)) {
                        AlertDialog(onDismissRequest = { }, confirmButton = {
                            Button(onClick = {
                                concentrationViewModel.resolveGameState(GameState.Idle)

                            }) {
                                Text("Reset")
                            }

                        }, title = {
                            Text(
                                text = "Your total score is: ${concentrationGridUiState.currentNumber + 1}"
                            )
                        })
                    }


                }
            }


        }

    }
}