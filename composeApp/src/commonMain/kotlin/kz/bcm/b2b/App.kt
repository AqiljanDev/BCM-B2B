package kz.bcm.b2b

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.Button
import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.compose_multiplatform
import kz.bcm.b2b.presentation.other.theme.MyTheme
import kz.bcm.b2b.presentation.ui.main.MainScreen

@Composable
@Preview
fun App() {
    MyTheme {
        MainScreen()
    }
}
