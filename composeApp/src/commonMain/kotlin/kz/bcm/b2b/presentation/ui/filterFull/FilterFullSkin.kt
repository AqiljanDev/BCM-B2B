package kz.bcm.b2b.presentation.ui.filterFull

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.DrawerValue
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.inter_medium
import bcm_b2b.composeapp.generated.resources.inter_regular
import kz.bcm.b2b.presentation.other.data.Route
import kz.bcm.b2b.presentation.other.theme.ColorDarkRed
import kz.bcm.b2b.presentation.ui.main.BottomNavigationBar
import kz.bcm.b2b.presentation.ui.main.DrawerContent
import org.jetbrains.compose.resources.Font


@Composable
fun FilterFullSkin(
    navController: NavController,
    category: String,
    slug: String,
    sort: String,
    f: String
) {
    val stateScaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))

    val stateReset = remember {
        mutableStateOf(false)
    }
    val stateClose = remember {
        mutableStateOf(false)
    }

    LaunchedEffect(stateReset.value) {
        println("reset state = ${stateReset.value}")
    }

    Scaffold(
        scaffoldState = stateScaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = "Фильтр",
                            fontSize = 16.sp,
                            fontFamily = FontFamily(Font(Res.font.inter_medium))
                        )
                    }
                },
                backgroundColor = Color.White,
                navigationIcon = {
                    Text(
                        text = "Отмена",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(Res.font.inter_regular)),
                        color = ColorDarkRed,
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .clickable {
                                stateClose.value = !stateClose.value
                            }
                    )
                },
                actions = {
                    Text(
                        text = "Сбросить",
                        fontSize = 14.sp,
                        fontFamily = FontFamily(Font(Res.font.inter_regular)),
                        color = ColorDarkRed,
                        modifier = Modifier
                            .padding(horizontal = 6.dp)
                            .clickable {
                                stateReset.value = !stateReset.value
                            }
                    )
                }
            )
        },
        drawerContent = { DrawerContent(navController) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->

        Box(
            modifier = Modifier.fillMaxSize()
                .padding(
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            FilterFullScreen(
                navController = navController,
                category = category,
                slug = slug,
                sort = sort,
                f = f,
                reset = stateReset,
                close = stateClose
            )
        }
    }
}