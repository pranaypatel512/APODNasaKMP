package com.example.common.ui.navigation

import kotlin.coroutines.CoroutineContext

actual open class ViewModel : InstanceKeeper.Instance, CoroutineScope, KoinComponent {
    actual override val coroutineContext: CoroutineContext =
        Dispatchers.Unconfined + SupervisorJob()

    override fun onDestroy() {
        coroutineContext.cancel()
    }
}