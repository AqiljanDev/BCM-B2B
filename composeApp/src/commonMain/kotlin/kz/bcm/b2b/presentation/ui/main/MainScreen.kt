package kz.bcm.b2b.presentation.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.ic_menu_burger
import bcm_b2b.composeapp.generated.resources.ic_search
import kotlinx.coroutines.launch
import kz.bcm.b2b.presentation.ui.catalog.CatalogScreen
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.currentKoinScope


@Preview
@Composable
fun MainScreen() {
    val stateScaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val coroutineScope = rememberCoroutineScope()

    val navController = rememberNavController()

    KoinContext {
        Scaffold(
            scaffoldState = stateScaffoldState,
            topBar = {
                TopAppBar(
                    title = {
                        Box(
                            modifier = Modifier.fillMaxWidth(),
                            contentAlignment = Alignment.Center
                        ) {
                            Text("My Centered Title")
                        }
                    },
                    backgroundColor = Color.White,
                    navigationIcon = {
                        IconButton(
                            onClick = {
                                coroutineScope.launch {
                                    stateScaffoldState.drawerState.open()
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
            drawerContent = { DrawerContent() },
            bottomBar = { BottomNavigationBar(navController) }
        ) { paddingValues ->

            Box(
                modifier = Modifier.fillMaxSize()
                    .padding(
                        bottom = paddingValues.calculateBottomPadding()
                    )
            ) {
                NavGraph(navController = navController)
            }
        }
    }
}










