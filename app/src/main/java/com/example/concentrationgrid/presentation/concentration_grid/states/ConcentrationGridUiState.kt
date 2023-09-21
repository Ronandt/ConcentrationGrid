package com.example.concentrationgrid.presentation.concentration_grid.states
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import com.example.concentrationgrid.config.AppConfig
import com.example.concentrationgrid.presentation.concentration_grid.ConcentrationGridScreen
import com.example.concentrationgrid.presentation.concentration_grid.states.GameState.*
/**
 * This class represents the UI state for the [ConcentrationGridScreen], which is used to manage the state of the concentration game grid.
 *
 * @param gameState Represents the current state of the game, such as [NotStarted], [Playing], [Won], and [Lost].
 * @param initialTimeInSeconds The initial duration of the game in seconds.
 * @param currentNumber The current highest number that the user has clicked on the grid.
 * @param gridNumberSequence The sequence of numbers displayed in the grid, shuffled for game randomness.
 * @param timeLeftInSeconds The remaining time in seconds for the current game session.
 *
 * @constructor Creates an instance of [ConcentrationGridUiState] with the specified initial values.
 */

@Immutable
data class ConcentrationGridUiState(
    val gameState: GameState = NotStarted,
    val initialTimeInSeconds: Long = 300L,
    val currentNumber: Int =  -1,
    val gridNumberSequence: List<Int> = (0..99).toList().shuffled(),
    val timeLeftInSeconds: Long = initialTimeInSeconds,
)