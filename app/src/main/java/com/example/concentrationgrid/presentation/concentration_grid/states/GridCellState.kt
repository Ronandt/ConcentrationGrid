package com.example.concentrationgrid.presentation.concentration_grid.states


sealed class GridCellState(var isError: Boolean = false) {
    object Scored: GridCellState() {
        override fun toString(): String {
            println(isError)
            return "SCORED"
        }
    }
    object Default: GridCellState() {
        override fun toString(): String {
            println(isError)
            return "DEFAULT"
        }
    }
}