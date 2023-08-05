package com.example.concentrationgrid.presentation.concentration_grid.components


import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color


@Composable
fun DefaultButton(text: String, onClick: ()-> Unit , modifier: Modifier = Modifier) {
    Button(onClick = onClick, modifier = modifier) {
        Text(text, color = Color.White)
    }
}
