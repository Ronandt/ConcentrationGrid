package com.example.concentrationgrid.presentation.concentration_grid.states

//TODO
sealed class GridCellState(isError: Boolean = true) {
    object Scored: GridCellState()
    object Default: GridCellState()
}