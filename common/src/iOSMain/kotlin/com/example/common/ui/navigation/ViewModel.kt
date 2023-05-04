package com.example.common.ui.navigation

import com.arkivanov.essenty.instancekeeper.InstanceKeeper
import kotlin.coroutines.CoroutineContext
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.cancel
import org.koin.core.component.KoinComponent

actual open class ViewModel : InstanceKeeper.Instance, CoroutineScope, KoinComponent {
    actual override val coroutineContext: CoroutineContext =
        Dispatchers.Unconfined + SupervisorJob()

    override fun onDestroy() {
        coroutineContext.cancel()
    }
}