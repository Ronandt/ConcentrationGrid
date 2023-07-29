package com.example.pager

import android.os.Bundle
import android.os.CountDownTimer
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.animateColor
import androidx.compose.animation.core.updateTransition
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.VerticalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.pager.ui.theme.PagerTheme
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.launch
import java.lang.Integer.max
import kotlin.random.Random

class MainActivity : ComponentActivity() {





    @OptIn(ExperimentalFoundationApi::class, ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val concentrationViewModel by viewModels<ConcentrationViewModel>()
        var mutableStateFlowTimer = concentrationViewModel.gameTime
        var gameState = concentrationViewModel.gameState



        val timer = concentrationViewModel.timer
        setContent {
            PagerTheme {
                var numbersToBeInserted = remember { concentrationViewModel.gameNumberSequence}
                var currentNumber by rememberSaveable {mutableStateOf(-1)}

                //var playing by remember {mutableStateOf(false)}
                var scope = rememberCoroutineScope()


                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    var timerInfo = mutableStateFlowTimer.collectAsState(initial = 300)

                    LaunchedEffect(gameState) {
                        if(gameState.value == GameState.Idle) {
                            mutableStateFlowTimer.value = 300L
                        }
                    }


                    Scaffold(topBar = {
                        TopAppBar(title = {Text("Concentration game", color = Color.White)}, colors = TopAppBarDefaults.mediumTopAppBarColors(containerColor = Color.Black))
                    }) {
                        BoxWithConstraints(modifier = Modifier.padding(it)) {
                            var orientation = this.maxHeight > this.maxWidth
                            Column() {

                                LazyVerticalGrid(columns = if(orientation) GridCells.Fixed(10) else GridCells.Fixed(20)) {
                                    items(100) {
                                        var onError by remember {mutableStateOf(false)}
                                        var transition = updateTransition(targetState = onError, "Error state")
                                        var colour = if (numbersToBeInserted[it] <= currentNumber) Color.Green else Color.White
                                        val backgroundColourTrans by transition.animateColor(label = "border color") { isSelected ->
                                            if (isSelected) Color.Red else Color.White
                                        }


                                        Text(numbersToBeInserted[it].toString().padStart(2, '0'), color = Color.Black, modifier = Modifier
                                            .background(if (!onError) colour else backgroundColourTrans)
                                            .clickable {
                                                if (currentNumber == -1 && !(gameState.value == GameState.Playing)) {
                                                    gameState.value = GameState.Playing
                                                    timer.start()
                                                }
                                                if ((currentNumber + 1) == numbersToBeInserted[it]) {
                                                    currentNumber += 1
                                                    if (currentNumber >= 99) {
                                                        gameState.value = GameState.Won
                                                    }
                                                } else {

                                                    scope.launch {
                                                        onError = true
                                                        delay(500L)
                                                        onError = false
                                                    }

                                                }

                                            }
                                            .padding(10.dp, 10.dp)
                                            , textAlign = TextAlign.Center, softWrap = false, overflow = TextOverflow.Visible, maxLines = 1)

                                    }

                                }

                                Column(modifier = Modifier
                                    .fillMaxSize()
                                    .padding(top = 14.dp), verticalArrangement =  Arrangement.Center, horizontalAlignment = Alignment.CenterHorizontally) {
                                    Text(text = timerInfo.value.floorDiv(60).toString()+ ":" + timerInfo.value.mod(60).toString().padStart(2, '0'), fontSize = 20.sp, fontWeight = FontWeight.Bold)

                                    Text(text = "Current Number: ${max(currentNumber, 0)}", fontSize = 20.sp, fontWeight = FontWeight.Bold, modifier = Modifier.padding(top = 20.dp))
                                }


                            }

                            if(gameState.collectAsState().value == GameState.Won) {
                                AlertDialog(onDismissRequest = {
                                                               }, confirmButton = {
                                    Button(onClick = { gameState.value = GameState.Idle
                                        timer.cancel()
                                        currentNumber = -1
                                    }) {
                                        Text("Reset")
                                    }

                                                                                                }, title = { Text(
                                    text = "You won in ${timerInfo.value.floorDiv(60).toString()+ " minutes and " + timerInfo.value.mod(60).toString().padStart(2, '0')} seconds"
                                )})
                            }
                            if((gameState.collectAsState().value == GameState.Lost)) {
                                AlertDialog(onDismissRequest = {  }, confirmButton = {
                                    Button(onClick = { gameState.value = GameState.Idle
                                        currentNumber = -1

                                    timer.cancel()

                                    }) {
                                        Text("Reset")
                                    }

                                }, title = { Text(
                                    text = "Your total score is: ${currentNumber + 1}"
                                )})
                            }


                        }
                    }


                    }

                }
            }
        }
    }


@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    PagerTheme {
        Greeting("Android")
    }
}