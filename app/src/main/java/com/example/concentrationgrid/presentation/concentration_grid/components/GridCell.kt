package com.example.concentrationgrid.presentation.concentration_grid.components

import android.annotation.SuppressLint
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.example.concentrationgrid.presentation.concentration_grid.ConcentrationGridEvent
import com.example.concentrationgrid.presentation.concentration_grid.states.GameState
import com.example.concentrationgrid.presentation.concentration_grid.theme.Green100
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@Composable
fun GridCell(value: Int, currentNumber: Int, isError: Boolean, @SuppressLint("ModifierParameter") eventModifier: Modifier) {
    val colour =
        if (value <= currentNumber) Green100 else Color.White
    val colourTransition by animateColorAsState(if(isError) Color.Red else colour)

    Text(
        value.toString()
            .padStart(2, '0'),
        color = if(colour != Green100 && !isError) Color.Black else Color.White,
        modifier = Modifier
            .background(colourTransition)
            .then(eventModifier)
            .padding(10.dp, 10.dp),
        textAlign = TextAlign.Center,
        softWrap = false,
        overflow = TextOverflow.Visible,
        maxLines = 1
    )
}