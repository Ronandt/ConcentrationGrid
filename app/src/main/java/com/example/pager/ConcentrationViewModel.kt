package com.example.pager

import android.os.CountDownTimer
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow

class ConcentrationViewModel: ViewModel() {
    var gameState = MutableStateFlow(GameState.Idle)
    var gameTime = MutableStateFlow(300L)
    var gameNumberSequence = (0..99).toList().shuffled().toTypedArray()
    var timer =  object : CountDownTimer(300000, 1000) {
        override fun onTick(millisUntilFinished: Long) {
            gameTime.value = millisUntilFinished/1000
        }

        override fun onFinish() {
            gameState.value = GameState.Lost

        }
    }
}