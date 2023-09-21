package com.example.concentrationgrid.presentation.grid_settings

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.concentrationgrid.GridSettings
import com.example.concentrationgrid.data.repository.GridSettingsRepository
import com.example.concentrationgrid.presentation.grid_settings.states.GridSettingsUiState
import com.example.concentrationgrid.presentation.grid_settings.states.ScreenState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.buffer
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class GridSettingsViewModel @Inject constructor(private val gridSettingsRepository: GridSettingsRepository): ViewModel() {
    private val _gridSettingsUiState = MutableStateFlow(GridSettingsUiState(screenState = ScreenState.Loading))
    val gridSettingsUiState = _gridSettingsUiState.asStateFlow()

    init {
        viewModelScope.launch {
             gridSettingsRepository.obtainGridSettings().distinctUntilChanged().collectLatest { gridSettings ->
                _gridSettingsUiState.update { uiState -> uiState.copy(shufflingEnabled = gridSettings.shufflingEnabled,
                    screenState = ScreenState.Ready)
                }
            }

        }

    }
     fun onEvent(gridSettingsEvent: GridSettingsEvent) {
        when(gridSettingsEvent) {
            is GridSettingsEvent.ToggleShufflingSettings -> {
                viewModelScope.launch {
                    gridSettingsRepository.configureGridSettings(gridSettingsEvent.enableShuffling, 10)
                }

            }


        }
    }



}