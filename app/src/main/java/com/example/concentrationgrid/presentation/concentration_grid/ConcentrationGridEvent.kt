package com.example.concentrationgrid.presentation.concentration_grid

import com.example.concentrationgrid.presentation.concentration_grid.states.GameState

/**
 * This sealed interface represents events that can occur in the Concentration Grid game.
 * Different types of events can be triggered during gameplay to communicate actions or state changes.
 */
sealed interface ConcentrationGridEvent {
    /**
     * Represents an event where a grid cell is clicked by the player.
     *
     * @property gridCellIndex The index of the clicked grid cell.
     * @property onError A callback that can be invoked when the user selects the wrong grid cell.
     */
    data class ClickedGridCell(
        val gridCellIndex: Int,
        val onError: () -> Unit
    ) : ConcentrationGridEvent

    /**
     * Represents an event to resolve the game state to a requested state.
     *
     * @property requestedGameState The requested game state to be resolved.
     */
    data class ResolveGameState(
        val requestedGameState: GameState
    ) : ConcentrationGridEvent
}
