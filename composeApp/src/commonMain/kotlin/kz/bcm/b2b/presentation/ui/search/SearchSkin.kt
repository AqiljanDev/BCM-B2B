package kz.bcm.b2b.presentation.ui.search

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.DrawerValue
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.rememberDrawerState
import androidx.compose.material.rememberScaffoldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.ic_back
import bcm_b2b.composeapp.generated.resources.ic_menu_burger
import bcm_b2b.composeapp.generated.resources.ic_search
import bcm_b2b.composeapp.generated.resources.logo_horizontal
import io.ktor.util.valuesOf
import kotlinx.coroutines.launch
import kz.bcm.b2b.presentation.other.data.Route
import kz.bcm.b2b.presentation.ui.auth.CustomOutlinedTextField
import kz.bcm.b2b.presentation.ui.catalog.CatalogScreen
import kz.bcm.b2b.presentation.ui.main.BottomNavigationBar
import kz.bcm.b2b.presentation.ui.main.DrawerContent
import org.jetbrains.compose.resources.painterResource


@Composable
fun SearchSkin(navController: NavController) {
    val stateScaffoldState = rememberScaffoldState(rememberDrawerState(DrawerValue.Closed))
    val coroutineScope = rememberCoroutineScope()

    val fieldSearch = remember {
        mutableStateOf("")
    }
    val focusManager = LocalFocusManager.current

    Scaffold(
        scaffoldState = stateScaffoldState,
        topBar = {
            TopAppBar(
                title = {
                    Box(
                        modifier = Modifier.fillMaxWidth().padding(vertical = 4.dp),
                        contentAlignment = Alignment.CenterStart
                    ) {

                        // Hint Text
                        if (fieldSearch.value.isEmpty()) {
                            Text(
                                text = "Поиск продуктов...", // Replace with your hint
                                fontSize = 15.sp,
                                color = Color.Gray,
                                modifier = Modifier.padding(start = 8.dp)
                            )
                        }

                        BasicTextField(
                            value = fieldSearch.value,
                            onValueChange = { fieldSearch.value = it },
                            modifier = Modifier
                                .fillMaxWidth()
                                .border(
                                width = 1.dp,
                                color = Color.LightGray,
                                shape = RoundedCornerShape(4.dp))
                                .padding(10.dp),
                            singleLine = true,
                            textStyle = TextStyle(fontSize = 15.sp),
                            keyboardOptions = KeyboardOptions.Default.copy(
                                imeAction = ImeAction.Done
                            ),
                            keyboardActions = KeyboardActions(
                                onDone = {
                                    // Снимаем фокус при нажатии на кнопку Done на клавиатуре
                                    focusManager.clearFocus()
                                }
                            )
                        )
                    }
                },
                backgroundColor = Color.White,
                navigationIcon = {
                    IconButton(
                        onClick = {
                            navController.popBackStack()
                        },
                    ) {
                        Icon(
                            painter = painterResource(Res.drawable.ic_back),
                            contentDescription = null
                        )
                    }
                },
                actions = {
                    IconButton(
                        onClick = {
                            focusManager.clearFocus()
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
        drawerContent = { DrawerContent(navController) },
        bottomBar = { BottomNavigationBar(navController) }
    ) { paddingValues ->

        Box(
            modifier = Modifier.fillMaxSize()
                .padding(
                    bottom = paddingValues.calculateBottomPadding()
                )
        ) {
            SearchScreen(navController, fieldSearch)
        }
    }
}
