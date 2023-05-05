package com.example.common.ui.screen.home

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.example.common.domain.model.APODPictureItem

@Parcelize
data class HomeUiState(
    val isLoading: Boolean = true,
    val error: String? = null,
    val apodPictureItemList: List<APODPictureItem>? = emptyList(),
):Parcelable