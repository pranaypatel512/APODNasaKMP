package com.example.common.ui.navigation // ktlint-disable filename

import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.window.ComposeUIViewController
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.example.common.App
import com.example.common.di.initKoin
import platform.UIKit.UIViewController


fun MainViewController(): UIViewController {
    initKoin()
    return ComposeUIViewController {
        val lifecycle = LifecycleRegistry()
        val rootComponentContext = DefaultComponentContext(lifecycle = lifecycle)

        CompositionLocalProvider(LocalComponentContext provides rootComponentContext) {
            App()
        }
    }
}
