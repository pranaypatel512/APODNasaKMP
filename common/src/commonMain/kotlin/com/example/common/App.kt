package com.example.common

import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.Composable
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.slide
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.stackAnimation
import com.example.common.ui.navigation.AppNavigator
import com.example.common.ui.navigation.RoutedContent
import com.example.common.ui.navigation.Router
import com.example.common.ui.navigation.rememberRouter
import com.example.common.ui.screen.details.DetailsScreen
import com.example.common.ui.screen.home.HomeScreen
import com.example.common.ui.theme.APODNasaAndroidTheme

@Composable
fun App() {
    APODNasaAndroidTheme {
        Surface(color = MaterialTheme.colors.surface) {
            val router: Router<AppNavigator> =
                rememberRouter(AppNavigator::class, listOf(AppNavigator.APODPicturesListScreen))
            RoutedContent(
                router = router,
                animation = stackAnimation(slide())
            ) { screen ->
                when (screen) {
                    is AppNavigator.APODPictureOverview -> {
                       DetailsScreen(screen.pictureItem)
                    }
                    AppNavigator.APODPicturesListScreen -> {
                        HomeScreen()
                    }
                }
            }
        }
    }
}
