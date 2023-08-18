package com.example.concentrationgrid.presentation.concentration_grid

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.concentrationgrid.data.repository.GridSettingsRepository
import com.example.concentrationgrid.presentation.concentration_grid.states.ConcentrationGridUiState
import com.example.concentrationgrid.presentation.concentration_grid.states.GameState
import com.example.concentrationgrid.presentation.concentration_grid.states.SettingsUiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class ConcentrationViewModel @Inject constructor(
    private val gridSettingsRepository: GridSettingsRepository
) : ViewModel() {
    private val _settingsState = MutableStateFlow(SettingsUiState())
    private val _concentrationGridState = MutableStateFlow(ConcentrationGridUiState())
    val concentrationGridState: StateFlow<ConcentrationGridUiState> =
        _concentrationGridState.asStateFlow()


    private val timer =
        object : CountDownTimer(_concentrationGridState.value.initialTimeInSeconds * 1000, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                _concentrationGridState.update {
                    println(millisUntilFinished / 1000)
                    it.copy(timeLeftInSeconds = millisUntilFinished / 1000)
                }
            }

            override fun onFinish() {
                resolveGameState(GameState.Lost)

            }
        }

    init {
        viewModelScope.launch {

            while (true) {
                if (concentrationGridState.value.gameState == GameState.NotStarted) shuffleGridSequence()
                delay(100L)
            }
        }

        viewModelScope.launch {
            gridSettingsRepository.obtainGridSettings().distinctUntilChanged().collectLatest { gridSettings ->
                if(gridSettings.shufflingEnabled != _settingsState.value.shufflingEnabled || gridSettings.shufflingRate != _settingsState.value.shufflingRateInSeconds) {
                    _settingsState.update {
                        it.copy(
                            shufflingEnabled = gridSettings.shufflingEnabled,
                            shufflingRateInSeconds = gridSettings.shufflingRate,
                        )
                    }
                    resolveGameState(GameState.NotStarted)
                }
            }

        }


        //This definitely has a problem, maybe use timer
            viewModelScope.launch {
                while(true) {

                    if(_settingsState.value.shufflingEnabled
                        && _concentrationGridState.value.gameState == GameState.Playing) {

                        shuffleGridSequence()
                    }
                    delay(1000L * _settingsState.value.shufflingRateInSeconds)
                }


            }





    }

    private fun resolveGameState(requestedGameState: GameState) {
        //If somehow someone finds a way to instantly win the game by skipping the gameplay
        if (requestedGameState in listOf(
                GameState.Lost,
                GameState.Won
            ) && _concentrationGridState.value.gameState == GameState.NotStarted
        ) {
            throw IllegalStateException("Illegal state from Idle to a final state")
        }
        _concentrationGridState.update {
            it.copy(gameState = requestedGameState)
        }
        //Timer is stopped if any state changes
        when (_concentrationGridState.value.gameState) {

            GameState.NotStarted -> {
                timer.cancel()
                _concentrationGridState.update {
                   ConcentrationGridUiState()
                }
            }

            GameState.Playing -> {
                timer.start()
            }

            GameState.Lost -> {
                timer.cancel()
            }

            GameState.Won -> {
                timer.cancel()
            }
        }

    }

    fun onEvent(uiEvent: ConcentrationGridEvent) {
        when (uiEvent) {
            is ConcentrationGridEvent.ClickedGridCell -> {
                if ((_concentrationGridState.value.currentNumber + 1) == _concentrationGridState.value.gridNumberSequence[uiEvent.gridCellIndex]) {
                    updateCurrentScore()
                    if (_concentrationGridState.value.currentNumber >= 99) resolveGameState(
                        GameState.Won
                    )
                } else {
                    uiEvent.onError()
                }


            }

            is ConcentrationGridEvent.ResolveGameState -> {
                resolveGameState(uiEvent.requestedGameState)
            }


        }
    }


    private fun updateCurrentScore() {
        _concentrationGridState.update {
            it.copy(currentNumber = it.currentNumber + 1)
        }
    }

    private fun shuffleGridSequence() {
        _concentrationGridState.update {
            it.copy(gridNumberSequence = it.gridNumberSequence.shuffled())
        }
    }


}

