package kz.bcm.b2b

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import kotlinx.coroutines.Dispatchers
import kz.bcm.b2b.di.dataModule
import kz.bcm.b2b.di.domainModule
import kz.bcm.b2b.di.viewModelModule
import org.koin.core.context.startKoin

fun main() = application {

    startKoin {
        modules(dataModule, domainModule, viewModelModule)
    }

    Window(
        onCloseRequest = ::exitApplication,
        title = "BCM-B2B",
    ) {
        App()
    }
}
