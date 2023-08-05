package com.example.concentrationgrid.presentation.concentration_grid

import com.example.concentrationgrid.presentation.concentration_grid.states.GameState

sealed interface ConcentrationGridEvent {
    data class ClickedGridCell(val gridCellIndex: Int, val onError: () -> Unit): ConcentrationGridEvent
    data class ResolveGameState(val requestedGameState: GameState): ConcentrationGridEvent



}
