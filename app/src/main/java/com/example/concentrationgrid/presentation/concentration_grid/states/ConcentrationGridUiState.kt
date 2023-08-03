package com.example.concentrationgrid.presentation.concentration_grid.states
data class ConcentrationGridUiState(
    val gameState: GameState = GameState.Idle,
    val initialTimeInSeconds: Long = 300L,
    val currentNumber: Int =  -1,
    val gridNumberSequence: List<Int> = (0..99).toList().shuffled(),
    val timeLeftInSeconds: Long = initialTimeInSeconds
)