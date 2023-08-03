package com.example.concentrationgrid.presentation.concentration_grid

sealed class ConcentrationGridEvent {
    data class ClickedGridCell(val gridCellIndex: Int, val onError: () -> Unit): ConcentrationGridEvent()




}
