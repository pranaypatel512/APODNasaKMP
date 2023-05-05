package com.example.common.ui.screen.details

import com.example.common.domain.model.APODPictureItem

data class DetailsUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val apodPictureItem: APODPictureItem? = null,
)
