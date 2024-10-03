package kz.bcm.b2b.presentation.ui.filterFull

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.Text
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.ic_right_arrow
import bcm_b2b.composeapp.generated.resources.ic_right_arrow_simple
import bcm_b2b.composeapp.generated.resources.inter_medium
import kotlinx.coroutines.launch
import kz.bcm.b2b.presentation.other.data.Route
import kz.bcm.b2b.presentation.other.data.SortItem
import kz.bcm.b2b.presentation.other.theme.ColorMainOrange
import kz.bcm.b2b.presentation.other.theme.ColorWhiteSmoke
import kz.bcm.b2b.presentation.other.toggleItem
import kz.bcm.b2b.presentation.ui.filter.SortBottomList
import kz.bcm.b2b.presentation.viewmodel.FilterFullViewModel
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject


@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterialApi::class)
@Composable
fun FilterFullScreen(
    navController: NavController,
    category: String,
    slug: String,
    sort: String,
    f: String,
    reset: MutableState<Boolean>,
    close: MutableState<Boolean>
) {
    val viewModel: FilterFullViewModel = koinInject()
    val coroutineScope = rememberCoroutineScope()
    val stateBottomSheet = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)

    val stateCharacter = viewModel.character.collectAsState()
    val stateTitle = viewModel.title.collectAsState()
    val savedStateH = navController.currentBackStackEntry?.savedStateHandle

    var currentSlug by remember {
        mutableStateOf(
            savedStateH?.getStateFlow<String>("slug", "index")?.value ?: "index"
        )
    }
    var currentSort by remember {
        mutableStateOf(
            SortItem.entries.find { it.translate == sort } ?: SortItem.NEW
        )
    }
    var currentF by remember {
        println("parametr = f: $f")
        mutableStateOf(
            if (f != ".") f.split(".") else emptyList()
        )
    }
    var currentMin by remember {
        mutableStateOf<Int?>(null)
    }
    var currentMax by remember {
        mutableStateOf<Int?>(null)
    }

    var firstReset by remember {
        mutableStateOf(true)
    }
    var firstClose by remember {
        mutableStateOf(true)
    }


    LaunchedEffect(Unit, currentSlug, currentMin, currentF) {
        viewModel.getCharacter(currentSlug, currentMin, currentF.joinToString(separator = "."))
        viewModel.getTitle(currentSlug)
    }

    LaunchedEffect(
        Unit,
        stateTitle,
        currentSlug,
        currentSort,
        currentF,
        currentMax,
        currentMin
    ) {
        println("filter full -> current = currentCategory: ${stateTitle.value}, currentSlug: $currentSlug, currentSort: $currentSort, currentF: $currentF, currentMax: $currentMax, currentMin: $currentMin")
    }


    LaunchedEffect(reset.value) {
        println("le: rv = ${reset.value} = $firstReset")
        if (!firstReset) {
            currentSlug = "index"
            currentSort = SortItem.NEW
            currentF = listOf()
            currentMin = null
            currentMax = null
            println("Launch effect reset = $reset")
        }
        firstReset = false
        println("le: fr = $firstReset")
    }

    LaunchedEffect(close.value) {
        if (!firstClose) {
            navController.popBackStack()
        }
        firstClose = false
    }


    ModalBottomSheetLayout(
        sheetState = stateBottomSheet,
        sheetContent = {
            SortBottomList(
                stateSortItem = currentSort,
                click = {
                    coroutineScope.launch {
                        stateBottomSheet.hide()
                    }
                    currentSort = it
                }
            )

        },
        modifier = Modifier
            .fillMaxSize(),
        sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
    ) {

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.BottomCenter) {

            LazyColumn(
                modifier = Modifier.fillMaxSize().background(Color.LightGray),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(6.dp)
            ) {

                item {
                    Column(
                        modifier = Modifier.background(Color.LightGray),
                        verticalArrangement = Arrangement.spacedBy(1.dp)
                    ) {
                        CharacterItem(
                            title = "Сортировка",
                            value = currentSort.translate,
                            click = {
                                coroutineScope.launch {
                                    stateBottomSheet.show()
                                }
                            }
                        )

                        CharacterItem(
                            title = "Категории",
                            value = stateTitle.value,
                            click = {
                                navController.navigate("${Route.CATALOG_LIST.route}/$currentSlug")
                            }
                        )
                    }
                }

                item {

                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .background(Color.White)
                            .padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(10.dp)
                    ) {

                        Text(
                            text = "Цена",
                            fontSize = 13.sp
                        )

                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceEvenly,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                CustomBasicFieldPrice(
                                    first = "от",
                                    item = if (currentMin != null) "$currentMin ₸" else "",
                                    onValue = { currentMin = it.toIntOrNull() }
                                )
                            }

                            Spacer(modifier = Modifier.width(10.dp).height(30.dp))

                            Box(
                                modifier = Modifier.weight(1f),
                                contentAlignment = Alignment.Center
                            ) {
                                CustomBasicFieldPrice(
                                    first = "до",
                                    item = if (currentMax != null) "$currentMax ₸" else "",
                                    onValue = { currentMax = it.toIntOrNull() }
                                )
                            }
                        }
                    }
                }

                items(stateCharacter.value.characters) { char ->
                    Column(
                        modifier = Modifier.fillMaxWidth().background(Color.White).padding(10.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Text(
                            text = char.title,
                            fontSize = 15.sp,
                            fontFamily = FontFamily(Font(Res.font.inter_medium))
                        )

                        FlowRow(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.spacedBy(16.dp),
                            verticalArrangement = Arrangement.spacedBy(8.dp)
                        ) {

                            char.characters.forEach { charValue ->

                                val stateChecked = currentF.any { charValue.id1c == it }

                                Text(
                                    text = charValue.title,
                                    fontSize = 10.sp,
                                    modifier = Modifier.border(
                                        width = if (stateChecked) 2.dp else 1.dp,
                                        color = if (stateChecked) ColorMainOrange else Color.LightGray,
                                        shape = RoundedCornerShape(12.dp)
                                    ).padding(7.dp)
                                        .clickable(
                                            indication = null,
                                            interactionSource = remember { MutableInteractionSource() }
                                        ) {
                                            currentF = currentF.toggleItem(item = charValue.id1c)
                                        }
                                )
                            }

                        }
                    }
                }
            }

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(ColorWhiteSmoke)
                    .padding(horizontal = 25.dp, vertical = 15.dp),
                contentAlignment = Alignment.Center
            ) {

                Text(
                    text = "Показать товары",
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_medium)),
                    color = Color.White,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth(1f)
                        .clip(RoundedCornerShape(4.dp))
                        .background(ColorMainOrange)
                        .padding(vertical = 13.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {

                            println("f: $f === fList: ${currentF.joinToString(separator = ".")}")

                            navController.previousBackStackEntry?.savedStateHandle?.apply {
                                set("slug", currentSlug)
                                set("sort", currentSort.message)
                                set("f", currentF.joinToString(separator = "."))
                                set("min", currentMin)
                                set("max", currentMax)
                            }
                            navController.popBackStack()
                        }
                )

            }
        }

    }
}


@Composable
private fun CharacterItem(
    title: String,
    value: String,
    click: () -> Unit
) {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(10.dp)
            .clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                click()
            },
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {

            Text(
                text = title,
                fontSize = 15.sp
            )

            Text(
                text = value,
                fontSize = 13.sp,
                color = Color.Gray
            )
        }

        Icon(
            painter = painterResource(Res.drawable.ic_right_arrow_simple),
            contentDescription = "right arrow",
            tint = Color.Gray
        )
    }
}


@Composable
fun CustomBasicFieldPrice(
    first: String,
    item: String,
    onValue: (String) -> Unit
) {
    var newItem by remember { mutableStateOf(item) }

    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {

        Text(
            text = first,
            fontSize = 17.sp,
            color = Color.Gray
        )

        BasicTextField(
            value = newItem,
            onValueChange = {
                newItem = it
                onValue(it)
            },
            singleLine = true,
            modifier = Modifier
                .border(
                    width = 1.dp,
                    color = Color.Gray,
                    shape = RoundedCornerShape(4.dp)
                ).padding(horizontal = 18.dp, vertical = 8.dp),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Next
            )
        )
    }

}














