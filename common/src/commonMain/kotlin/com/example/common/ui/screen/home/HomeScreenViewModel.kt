package com.example.common.ui.screen.home

import com.example.common.data.sources.NasaPicturesRepositoryImpl
import com.example.common.domain.isLoading
import com.example.common.domain.onFailure
import com.example.common.domain.onSuccess
import com.example.common.ui.navigation.SavedStateHandle
import com.example.common.ui.navigation.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.inject

class HomeScreenViewModel(private val savedState: SavedStateHandle) : ViewModel() {
    private val repository: NasaPicturesRepositoryImpl by inject()

    private val _state: MutableStateFlow<HomeUiState> =
        MutableStateFlow(savedState.get() ?: HomeUiState())
    val state: StateFlow<HomeUiState> = _state.asStateFlow()

    init {
        fetchAPODNasaPictures()
        saveStateUpdates()
    }

    fun fetchAPODNasaPictures() = launch {
        repository.getAPODPictures(25).collect { moviesResult ->
            moviesResult.isLoading { isLoading ->
                _state.update { it.copy(isLoading = isLoading) }
            }.onSuccess { apodPictureItemList ->
                _state.update { it.copy(apodPictureItemList = apodPictureItemList) }
            }.onFailure { error ->
                _state.update { it.copy(error = error.message) }
            }
        }
    }

    private fun saveStateUpdates() {
        launch {
            state.collectLatest {
                savedState.set(it)
            }
        }
    }
}
