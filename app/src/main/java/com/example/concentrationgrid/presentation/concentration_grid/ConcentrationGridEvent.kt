package com.example.concentrationgrid.presentation.concentration_grid

sealed class ConcentrationGridEvent {
    data class ClickedGridCell(val gridCellIndex: Int): ConcentrationGridEvent()
    data class ShowGridCellError(val gridCellIndex: Int): ConcentrationGridEvent()



}
