package kz.bcm.b2b.presentation.ui.filter

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.ic_menu_burger
import bcm_b2b.composeapp.generated.resources.ic_search
import bcm_b2b.composeapp.generated.resources.logo_horizontal
import kotlinx.coroutines.launch
import kz.bcm.b2b.presentation.ui.catalog.CatalogScreen
import kz.bcm.b2b.presentation.ui.main.BottomNavigationBar
import kz.bcm.b2b.presentation.ui.main.DrawerContent
import org.jetbrains.compose.resources.painterResource


@Composable
fun FilterSkin(navController: NavController) {
    val stateScaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val coroutineScope = rememberCoroutineScope()

    Scaffold(
        scaffoldState = stateScaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.logo_horizontal),
                            contentDescription = "logo",
                            modifier = Modifier.fillMaxSize(),
                            tint = Color.Unspecified
                        )
                    }
                },
                backgroundColor = Color.White,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            coroutineScope.launch {
                                stateScaffoldState.drawerState.open()
                                println("nav click")
                            }
                        },
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_menu_burger),
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            println("Search click")
                        },
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_search),
                            contentDescription = null,
                            modifier = Modifier.size(25.dp)
                        )
                    }
                }
            )
        },
        drawerContent = { DrawerContent(navController) }
    ) { paddingValues ->

        FilterScreen(navController)
    }
}