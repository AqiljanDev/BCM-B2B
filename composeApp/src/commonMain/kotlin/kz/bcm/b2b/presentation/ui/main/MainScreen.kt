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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.ic_cart
import bcm_b2b.composeapp.generated.resources.ic_menu_burger
import bcm_b2b.composeapp.generated.resources.ic_search
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun MainScreen() {
    val stateScaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val coroutineScope = rememberCoroutineScope()
    val selectedItem = remember {
        mutableStateOf(0)
    }

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
                        Icon(painter = painterResource(Res.drawable.ic_menu_burger), contentDescription = null)
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            println("Search click")
                        },
                    ) {
                        Icon(painter = painterResource(Res.drawable.ic_search), contentDescription = null)
                    }
                }
            )
        },
        drawerContent = { DrawerContent() },
        bottomBar = { BottomNavigationBar(selectedItem) }
    ) {

        when(selectedItem.value) {
            0 -> {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text("Catalog screen")
                }
                println("0 - Screen item #${selectedItem.value + 1}")
            }
            1 -> {
                println("1 -Screen item #${selectedItem.value + 1}")
            }
            2 -> {
                println("2 - Screen item #${selectedItem.value + 1}")
            }
            3 -> {
                println("3 - Screen item #${selectedItem.value + 1}")
            }
            4 -> {
                println("4 - Screen item #${selectedItem.value + 1}")
            }
        }
    }
}