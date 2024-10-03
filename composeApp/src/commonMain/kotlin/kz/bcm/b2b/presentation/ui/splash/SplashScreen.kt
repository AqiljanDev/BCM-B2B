package kz.bcm.b2b.presentation.ui.splash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.bcm_storis
import bcm_b2b.composeapp.generated.resources.ic_logo
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject


@Composable
fun SplashScreen() {
    val viewModel: SplashViewModel = koinInject()


    LaunchedEffect(Unit) {
        viewModel.checkToken()
    }

    Box(
        modifier = Modifier.fillMaxSize()
            .background(Color.White),
        contentAlignment = Alignment.Center
    ) {

        Icon(
            painter = painterResource(Res.drawable.ic_logo),
            contentDescription = "logotype",
            modifier = Modifier.size(150.dp),
            tint = Color.Unspecified
        )
    }
}