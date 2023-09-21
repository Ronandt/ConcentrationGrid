package com.example.concentrationgrid.presentation.grid_settings.components

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.hoverable
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

@Composable
fun SelectionButton(title: String, onClick: () -> Unit, selected: Boolean, modifier: Modifier = Modifier) {
    val animateColor by animateColorAsState(targetValue =if(selected) Color.Black else Color.LightGray,
        label = "Button is selected"
    )
    Button(onClick = onClick, shape = RoundedCornerShape(10.dp), colors = ButtonDefaults.buttonColors(containerColor = animateColor), modifier = modifier) {
        Text(text = title)
    }
}
