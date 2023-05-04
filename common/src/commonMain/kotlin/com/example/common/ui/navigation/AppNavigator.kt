package com.example.common.ui.navigation

import com.arkivanov.essenty.parcelable.Parcelable
import com.arkivanov.essenty.parcelable.Parcelize
import com.example.common.domain.model.APODPictureItem

@Parcelize
sealed class AppNavigator : Parcelable {
    object APODPicturesListScreen : AppNavigator()
    data class APODPictureOverview(val pictureItem: APODPictureItem) : AppNavigator()
}
