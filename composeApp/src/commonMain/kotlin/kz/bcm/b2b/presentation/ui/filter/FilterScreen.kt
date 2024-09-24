package kz.bcm.b2b.presentation.ui.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Checkbox
import androidx.compose.material.CheckboxDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Icon
import androidx.compose.material.ModalBottomSheetLayout
import androidx.compose.material.ModalBottomSheetState
import androidx.compose.material.ModalBottomSheetValue
import androidx.compose.material.RadioButton
import androidx.compose.material.RadioButtonDefaults
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.ic_arrow_down
import bcm_b2b.composeapp.generated.resources.ic_filter
import bcm_b2b.composeapp.generated.resources.ic_sort
import bcm_b2b.composeapp.generated.resources.inter_bold
import bcm_b2b.composeapp.generated.resources.inter_medium
import bcm_b2b.composeapp.generated.resources.inter_regular
import kotlinx.coroutines.launch
import kz.bcm.b2b.data.dto.collectCharacters.CharacterItemDto
import kz.bcm.b2b.domain.data.collectCharacters.CharacterItem
import kz.bcm.b2b.presentation.other.data.Route
import kz.bcm.b2b.presentation.other.data.SortItem
import kz.bcm.b2b.presentation.other.theme.ColorMainGreen
import kz.bcm.b2b.presentation.other.theme.ColorMainOrange
import kz.bcm.b2b.presentation.other.theme.ColorWhiteSmoke
import kz.bcm.b2b.presentation.other.toggleItem
import kz.bcm.b2b.presentation.ui.catalog.Pagination
import kz.bcm.b2b.presentation.ui.catalog.ProductItem
import kz.bcm.b2b.presentation.viewmodel.FilterViewModel
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject


@OptIn(ExperimentalMaterialApi::class)
@Composable
fun FilterScreen(navController: NavController) {
    val viewModel: FilterViewModel = koinInject()
    val listState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    val stateProduct = viewModel.product.collectAsState()
    val stateCharacter = viewModel.character.collectAsState()

    val stateCompare = viewModel.compare.collectAsState()
    val stateFavorite = viewModel.favorite.collectAsState()
    val stateCart = viewModel.cart.collectAsState()


    val stateBottomSheet = rememberModalBottomSheetState(ModalBottomSheetValue.Hidden)
    var currBottom by remember {
        mutableStateOf(StateBottomSheet.SORT)
    }
    var stateCurrCharacter by remember {
        mutableStateOf<CharacterItem>(CharacterItemDto())
    }

    var currentCategory by remember {
        mutableStateOf("index")
    }
    var currentPager by remember {
        mutableStateOf(1)
    }
    var currentMin by remember {
        mutableStateOf<Int?>(null)
    }
    var currentMax by remember {
        mutableStateOf<Int?>(null)
    }
    var currentSort by remember {
        mutableStateOf(SortItem.NEW)
    }
    var currentF by remember {
        mutableStateOf(emptyList<String>())
    }

    LaunchedEffect(
        Unit,
        currentCategory,
        currentPager,
        currentMin,
        currentMax,
        currentSort,
        currentF
    ) {
        viewModel.getProduct(
            category = currentCategory,
            page = currentPager,
            min = currentMin,
            max = currentMax,
            sort = currentSort.message,
            f = currentF.joinToString(separator = ".")
        )

        viewModel.getCharacters(
            category = currentCategory,
            min = currentMin,
            f = currentF.joinToString(separator = ".")
        )

        listState.scrollToItem(0)
        println("laLaunchedEffect = current = category: $currentCategory, pager: $currentPager, min: $currentMin, max: $currentMax, sort: $currentSort, f: $currentF == ${currentF.joinToString { "." }}")
    }

    LaunchedEffect(stateCharacter) {
        println("stateCharacter = ${stateCharacter.value}")
    }

    LaunchedEffect(stateProduct) {
        viewModel.initializeData()

        println("initializeData in Catalog screen = ${stateProduct.value}")
    }

    ModalBottomSheetLayout(
        sheetState = stateBottomSheet,
        sheetContent = {
            if (StateBottomSheet.SORT == currBottom) {
                SortBottomList(
                    stateSortItem = currentSort,
                    click = {
                        coroutineScope.launch {
                            stateBottomSheet.hide()
                        }
                        currentSort = it
                    }
                )
            } else {
                CharacterBottomSheet(
                    item = stateCurrCharacter,
                    currentChar = currentF,
                    click = {
                        currentF = currentF.toggleItem(it)
                    }
                )
            }
        },
        modifier = Modifier
            .fillMaxSize(),
        sheetShape = RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp)
    ) {

        LazyColumn(
            modifier = Modifier.fillMaxSize().padding(bottom = 8.dp),
            state = listState,
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {

            item {
                Column {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(52.dp)
                            .padding(horizontal = 8.dp)
                            .background(Color.LightGray),
                        horizontalArrangement = Arrangement.spacedBy(1.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .fillMaxHeight()
                                .background(Color.White),
                            contentAlignment = Alignment.CenterStart
                        ) {
                            Text(
                                text = stateProduct.value.info.title,
                                fontSize = 18.sp,
                                fontFamily = FontFamily(Font(Res.font.inter_bold)),
                                textAlign = TextAlign.Center
                            )
                        }


                        Icon(
                            painter = painterResource(Res.drawable.ic_sort),
                            contentDescription = "sort",
                            tint = ColorMainOrange,
                            modifier = Modifier
                                .fillMaxHeight()
                                .aspectRatio(1f)
                                .background(Color.White)
                                .padding(13.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {
                                    coroutineScope.launch {
                                        currBottom = StateBottomSheet.SORT
                                        stateBottomSheet.show()
                                    }
                                }

                        )

                        Icon(
                            painter = painterResource(Res.drawable.ic_filter),
                            contentDescription = "filter",
                            tint = ColorMainOrange,
                            modifier = Modifier
                                .fillMaxHeight()
                                .aspectRatio(1f)
                                .background(Color.White)
                                .padding(13.dp)
                                .clickable(
                                    indication = null,
                                    interactionSource = remember { MutableInteractionSource() }
                                ) {

                                }
                        )
                    }

                    Spacer(
                        modifier = Modifier.fillMaxWidth().height(1.dp).background(Color.LightGray)
                    )
                }
            }

            item {
                LazyRow(
                    modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp),
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    contentPadding = PaddingValues(vertical = 3.dp)
                ) {
                    items(stateCharacter.value.characters.take(10)) { char ->
                        CharactersItem(
                            char,
                            currentF
                        ) {
                            coroutineScope.launch {
                                stateCurrCharacter = char
                                currBottom = StateBottomSheet.CHARACTER
                                stateBottomSheet.show()
                            }
                        }
                    }
                }
            }


            item {
                Spacer(modifier = Modifier.fillMaxWidth().height(7.dp).background(Color.LightGray))
            }


            items(stateProduct.value.products) { product ->
                Box(modifier = Modifier.fillMaxWidth().padding(horizontal = 8.dp)) {
                    ProductItem(
                        product = product,
                        compareList = stateCompare.value,
                        favoriteList = stateFavorite.value,
                        cartList = stateCart.value.products,
                        clickFavorite = { prodId -> viewModel.eventFavorite(prodId) },
                        clickCompare = { prodId -> viewModel.eventCompare(prodId) },
                        clickCart = { item, id -> viewModel.eventCart(item, id) },
                        clickRoot = { slug -> navController.navigate("${Route.CARD.route}/$slug") }
                    )
                }
            }


            if (stateCharacter.value.pages > 1) {

                item {
                    Pagination(
                        currentPage = currentPager,
                        totalPages = stateCharacter.value.pages
                    ) { page -> currentPager = page }
                }
            }
        }
    }
}


@Composable
fun CharactersItem(
    item: CharacterItem,
    currentChar: List<String>,
    click: () -> Unit
) {

    val listActive = item.characters.filter { char -> currentChar.any { char.id1c == it } }

    if (listActive.isEmpty()) {
        Row(
            modifier = Modifier.border(
                width = 1.dp,
                color = Color.LightGray,
                shape = RoundedCornerShape(9.dp)
            )
                .padding(horizontal = 8.dp, vertical = 3.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    click()
                },
            horizontalArrangement = Arrangement.spacedBy(4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = item.title,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(Res.font.inter_medium))
            )

            Icon(
                painter = painterResource(Res.drawable.ic_arrow_down),
                contentDescription = "down"
            )
        }
    } else {

        Row(
            modifier = Modifier.border(
                width = 2.dp,
                color = ColorMainOrange,
                shape = RoundedCornerShape(9.dp)
            ).clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    click()
                }
        ) {

            val title =
                if (listActive.size > 1) listActive[0].title + ", +${listActive.size - 1}" else listActive.first().title

            Text(
                text = title,
                fontSize = 12.sp,
                fontFamily = FontFamily(Font(Res.font.inter_medium)),
                modifier = Modifier.padding(horizontal = 8.dp, vertical = 7.dp)
            )
        }
    }
}


@Composable
private fun SortBottomList(
    stateSortItem: SortItem,
    click: (SortItem) -> Unit
) {
    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .background(Color.White)
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
            Spacer(
                modifier = Modifier
                    .width(40.dp)
                    .height(2.dp)
                    .background(Color.Gray)
            )
        }

        Text(
            text = "Сортировка",
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.inter_medium)),
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        LazyColumn(
            Modifier.background(Color.LightGray),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            items(SortItem.entries, key = null) { item ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .clickable {
                            click(item)
                        },
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    RadioButton(
                        selected = stateSortItem == item,
                        onClick = {
                            click(item)
                        },
                        colors = RadioButtonDefaults.colors(ColorMainOrange)
                    )
                    Text(
                        text = item.translate,
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(Res.font.inter_regular))
                    )
                }
            }
        }
    }
}


@Composable
fun CharacterBottomSheet(
    item: CharacterItem,
    currentChar: List<String>,
    click: (String) -> Unit
) {
    val selectedStates = remember {
        mutableStateMapOf<String, Boolean>().apply {
            item.characters.forEach { char ->
                this[char.id1c] = currentChar.any { char.id1c == it }
            }
        }
    }

    Column(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 8.dp, topEnd = 8.dp))
            .background(Color.White)
            .fillMaxWidth()
            .padding(horizontal = 5.dp, vertical = 8.dp),
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.TopCenter) {
            Spacer(
                modifier = Modifier
                    .width(40.dp)
                    .height(2.dp)
                    .background(Color.Gray)
            )
        }

        Text(
            text = item.title,
            fontSize = 18.sp,
            fontFamily = FontFamily(Font(Res.font.inter_medium)),
            modifier = Modifier
                .fillMaxWidth(),
            textAlign = TextAlign.Center
        )

        LazyColumn(
            Modifier.background(Color.LightGray),
            verticalArrangement = Arrangement.spacedBy(1.dp)
        ) {
            items(item.characters, key = null) { char ->

                val check = selectedStates[char.id1c] ?: false

                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .background(Color.White)
                        .clickable {
                            val newCheckState = !check
                            selectedStates[char.id1c] = newCheckState
                            click(char.id1c)
                        },
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {

                    Checkbox(
                        checked = check,
                        onCheckedChange = {
                            val newCheckState = !check
                            selectedStates[char.id1c] = newCheckState
                            click(char.id1c)
                        },
                        colors = CheckboxDefaults.colors(checkedColor = ColorMainOrange)
                    )

                    Text(
                        text = char.title,
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(Res.font.inter_regular))
                    )
                }
            }
        }
    }
}


private enum class StateBottomSheet {
    SORT,
    CHARACTER
}