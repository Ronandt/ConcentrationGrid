package com.example.concentrationgrid.presentation.concentration_grid.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.concentrationgrid.presentation.concentration_grid.theme.Green100


@Composable
fun GridCell(value: String,
                 scored: Boolean,
                 error: Boolean,
                 clickable: Boolean,
                 onClick: () -> Unit) {
    val colourTransition by animateColorAsState(
        when {
            error -> Color.Red
            scored -> Green100
            else -> Color.White
        }

    )

    Text(
        value
            .padStart(2, '0'),
        color = if(!error && !scored) Color.Black else Color.White,
        modifier = Modifier
            .background(colourTransition)
            .then(if(clickable) Modifier.clickable {
                onClick()
            } else Modifier)
            .padding(10.dp, 10.dp),
        textAlign = TextAlign.Center,
        softWrap = false,
        overflow = TextOverflow.Visible,
        fontSize = 18.sp,
        maxLines = 1,
        fontWeight = FontWeight.SemiBold
    )
}