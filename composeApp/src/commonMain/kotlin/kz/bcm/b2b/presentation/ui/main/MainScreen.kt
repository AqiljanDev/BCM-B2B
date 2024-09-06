package kz.bcm.b2b.presentation.ui.main

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.ic_cart
import bcm_b2b.composeapp.generated.resources.ic_menu_burger
import bcm_b2b.composeapp.generated.resources.ic_search
import kotlinx.coroutines.launch
import kz.bcm.b2b.presentation.other.data.UiState
import kz.bcm.b2b.presentation.viewmodel.CatalogViewModel
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.KoinContext
import org.koin.compose.currentKoinScope
import org.koin.compose.koinInject
import org.koin.core.annotation.KoinExperimentalAPI
import org.koin.core.context.KoinContext


@Preview
@Composable
fun MainScreen() {
    val stateScaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val coroutineScope = rememberCoroutineScope()
    val selectedItem = remember {
        mutableStateOf(0)
    }

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
                                contentDescription = null
                            )
                        }
                    }
                )
            },
            drawerContent = { DrawerContent() },
            bottomBar = { BottomNavigationBar(selectedItem) }
        ) {

            when (selectedItem.value) {
                0 -> {
                    val viewModel: CatalogViewModel = koinInject()
                    val stateCatalog = viewModel.catalog.collectAsState()

                    LaunchedEffect(Unit) {
                        viewModel.getFindOne(category = "index", page = 1)
                    }

                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

                        println("Catalog: ${stateCatalog.value?.info?.title}")
                    }
                    println("Screen item #${selectedItem.value + 1}")
                }

                1 -> {
                    println("Screen item #${selectedItem.value + 1}")
                }

                2 -> {
                    println("Screen item #${selectedItem.value + 1}")
                }

                3 -> {
                    println("Screen item #${selectedItem.value + 1}")
                }

                4 -> {
                    println("Screen item #${selectedItem.value + 1}")
                }
            }
        }
    }
}


@Composable
inline fun <reified T : ViewModel> koinViewModel(): T {
    val scope = currentKoinScope()
    return viewModel {
        scope.get<T>()
    }
}












