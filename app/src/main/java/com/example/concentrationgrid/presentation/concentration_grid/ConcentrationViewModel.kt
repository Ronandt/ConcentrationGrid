package com.example.concentrationgrid.presentation.concentration_grid

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.concentrationgrid.presentation.concentration_grid.states.ConcentrationGridUiState
import com.example.concentrationgrid.presentation.concentration_grid.states.GameState
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch



class ConcentrationViewModel: ViewModel() {
     private val _concentrationGridState = MutableStateFlow(ConcentrationGridUiState())
    val concentrationGridState: StateFlow<ConcentrationGridUiState> = _concentrationGridState.asStateFlow()



    private val timer =  object : CountDownTimer(_concentrationGridState.value.initialTimeInSeconds * 1000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            _concentrationGridState.update {
                println(millisUntilFinished/1000)
                it.copy(timeLeftInSeconds = millisUntilFinished/1000)
            }
        }

        override fun onFinish() {
            resolveGameState(GameState.Lost)

        }
    }

    init {
        viewModelScope.launch {
            while (true) {
                if (concentrationGridState.value.gameState == GameState.Idle) shuffleGridSequence()
                delay(100)
            }
        }

    }

    fun resolveGameState(requestedGameState: GameState) {
        if(requestedGameState in listOf(GameState.Lost, GameState.Won) && _concentrationGridState.value.gameState == GameState.Idle) {
            throw IllegalStateException("Illegal state from Idle to a final state")
        }
        _concentrationGridState.update {
            it.copy(gameState = requestedGameState)
        }
        //Timer is stopped if any state changes
        timer.cancel()
        when(_concentrationGridState.value.gameState) {

            GameState.Idle -> {

                _concentrationGridState.update {
                    it.copy(currentNumber = -1, timeLeftInSeconds = it.initialTimeInSeconds, gridNumberSequence = it.gridNumberSequence.shuffled())
                }
            }
            GameState.Playing -> {
                timer.start()
            }
            GameState.Lost -> {
                //Left Empty
            }
            GameState.Won -> {
                //Left Empty
            }
        }

    }

    fun updateCurrentScore() {
        _concentrationGridState.update {
            it.copy(currentNumber = it.currentNumber + 1)
        }
    }

    fun shuffleGridSequence() {
        _concentrationGridState.update {
            it.copy(gridNumberSequence = it.gridNumberSequence.shuffled())
        }
    }

}

