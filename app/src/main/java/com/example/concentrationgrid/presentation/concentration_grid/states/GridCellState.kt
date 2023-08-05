package com.example.concentrationgrid.presentation.concentration_grid.states

//TODO
sealed class GridCellState(var isError: Boolean = false) {

    object Scored: GridCellState()
    object Default: GridCellState()
}