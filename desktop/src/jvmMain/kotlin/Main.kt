import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.arkivanov.decompose.DefaultComponentContext
import com.arkivanov.essenty.lifecycle.LifecycleRegistry
import com.example.common.App
import com.example.common.di.initKoin
import org.koin.core.Koin

lateinit var koin: Koin
fun main() = application {
    val lifecycle = LifecycleRegistry()
    // Always create the root component outside Compose on the UI thread
    val root =
        runOnUiThread {
            val root = DefaultComponentContext(lifecycle = lifecycle)

        }
    koin = initKoin().koin
    Window(onCloseRequest = ::exitApplication) {
        App()
    }
}
