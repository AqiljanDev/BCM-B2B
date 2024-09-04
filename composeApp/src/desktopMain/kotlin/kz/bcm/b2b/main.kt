package kz.bcm.b2b

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "BCM-B2B",
    ) {
        App()
    }
}