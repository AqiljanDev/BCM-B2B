package kz.bcm.b2b.presentation.ui.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
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
import bcm_b2b.composeapp.generated.resources.ic_arrow_down
import bcm_b2b.composeapp.generated.resources.ic_filter
import bcm_b2b.composeapp.generated.resources.ic_sort
import bcm_b2b.composeapp.generated.resources.inter_medium
import kz.bcm.b2b.presentation.other.data.Route
import kz.bcm.b2b.presentation.other.theme.ColorWhiteSmoke
import kz.bcm.b2b.presentation.ui.catalog.ProductItem
import kz.bcm.b2b.presentation.viewmodel.FilterViewModel
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject


@Composable
fun FilterScreen(navController: NavController) {
    val viewModel: FilterViewModel = koinInject()
    val listState = rememberLazyListState()

    val stateProduct = viewModel.product.collectAsState()
    val stateCharacter = viewModel.character.collectAsState()

    val stateCompare = viewModel.compare.collectAsState()
    val stateFavorite = viewModel.favorite.collectAsState()
    val stateCart = viewModel.cart.collectAsState()

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
        mutableStateOf("")
    }
    var currentF by remember {
        mutableStateOf("")
    }

    LaunchedEffect(Unit, currentCategory, currentPager) {
        viewModel.getProduct(
            category = currentCategory,
            page = currentPager,
            min = currentMin,
            max = currentMax,
            sort = currentSort,
            f = currentF
        )

        listState.scrollToItem(0)
        println("getFindOne in Catalog screen")
    }

    LaunchedEffect(stateProduct) {
        viewModel.initializeData()

        println("initializeData in Catalog screen = ${stateProduct.value}")
    }

    LaunchedEffect(Unit, currentCategory) {

        viewModel.getCharacters(
            category = currentCategory,
            min = currentMin,
            f = currentF
        )
    }



    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(6.dp)
    ) {

        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = stateProduct.value.info.title,
                    modifier = Modifier.weight(1f)
                )

                Icon(
                    painter = painterResource(Res.drawable.ic_sort),
                    contentDescription = "sort"
                )

                Icon(
                    painter = painterResource(Res.drawable.ic_filter),
                    contentDescription = "filter"
                )
            }
        }

        item {
            LazyRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(6.dp),
                verticalAlignment = Alignment.CenterVertically,
                contentPadding = PaddingValues(6.dp)
            ) {
                items(stateCharacter.value.characters) { char ->
                    CharactersItem(char.title) {

                    }
                }
            }
        }

        items(stateProduct.value.products) { product ->
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
}


@Composable
fun CharactersItem(title: String, click: () -> Unit) {
    Row(
        modifier = Modifier.border(
            width = 1.dp,
            color = Color.LightGray,
            shape = RoundedCornerShape(3.dp)
        )
            .padding(3.dp)
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
            text = title,
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(Res.font.inter_medium))
        )

        Icon(
            painter = painterResource(Res.drawable.ic_arrow_down),
            contentDescription = "down"
        )
    }
}