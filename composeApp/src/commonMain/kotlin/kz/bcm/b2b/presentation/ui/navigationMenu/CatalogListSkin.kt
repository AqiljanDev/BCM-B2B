package kz.bcm.b2b.presentation.ui.navigationMenu

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController


@Composable
fun CatalogListSkin(navController: NavController) {

    Box(modifier = Modifier.fillMaxSize()) {
        Text("This screen")
    }
}