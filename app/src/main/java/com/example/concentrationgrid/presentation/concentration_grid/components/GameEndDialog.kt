package com.example.concentrationgrid.presentation.concentration_grid.components

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun GameEndDialog(title: String, description: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    AlertDialog(onDismissRequest = {}, confirmButton = {
        DefaultButton(onClick = onClick, text = "Reset")
    }, title = {
        Text(
            text = title
        )
    }, text = { Text(text = description)}, modifier = modifier)
}