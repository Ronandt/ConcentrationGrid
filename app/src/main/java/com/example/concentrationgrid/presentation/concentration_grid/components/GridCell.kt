package com.example.concentrationgrid.presentation.concentration_grid.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.concentrationgrid.presentation.concentration_grid.theme.Green100


@Composable
fun GridCell(value: Int, currentNumber: Int , isError: Boolean, modifier: Modifier) {
    val colourTransition by animateColorAsState(
        when {
            isError -> Color.Red
            value <= currentNumber -> Green100
            else -> Color.White
        }
    )

    Text(
        value.toString()
            .padStart(2, '0'),
        color = if(!isError && value > currentNumber) Color.Black else Color.White,
        modifier = Modifier
            .background(colourTransition)
            .then(modifier)
            .padding(10.dp, 10.dp),
        textAlign = TextAlign.Center,
        softWrap = false,
        overflow = TextOverflow.Visible,
        maxLines = 1
    )
}

//TODO create a good abstraction for grid-cell