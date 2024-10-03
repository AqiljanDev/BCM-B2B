package kz.bcm.b2b.presentation.ui.search

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import kz.bcm.b2b.presentation.other.data.Route
import kz.bcm.b2b.presentation.ui.catalog.ProductItem
import kz.bcm.b2b.presentation.viewmodel.SearchViewModel
import org.koin.compose.koinInject


@Composable
fun SearchScreen(
    navController: NavController,
    fieldState: MutableState<String>
) {
    val viewModel: SearchViewModel = koinInject()

    val stateProduct = viewModel.product.collectAsState()

    val stateCompare = viewModel.compare.collectAsState()
    val stateFavorite = viewModel.favorite.collectAsState()
    val stateCart = viewModel.cart.collectAsState()
    val stateDiscount = viewModel.userDiscount.collectAsState()

    LaunchedEffect(fieldState.value) {
        println("field state: ${fieldState.value}")
        viewModel.getProduct(fieldState.value)
    }

    LaunchedEffect(stateProduct) {
        viewModel.initializeData()

        println("initializeData in Catalog screen = ${stateProduct.value}")
    }


    LazyColumn(
        modifier = Modifier.fillMaxSize().padding(8.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        items(stateProduct.value) { product ->
            ProductItem(
                product = product,
                compareList = stateCompare.value,
                favoriteList = stateFavorite.value,
                cartList = stateCart.value.products,
                discount = stateDiscount.value,
                clickFavorite = { prodId -> viewModel.eventFavorite(prodId) },
                clickCompare = { prodId -> viewModel.eventCompare(prodId) },
                clickCart = { item, id -> viewModel.eventCart(item, id) },
                clickRoot = { slug -> navController.navigate("${Route.CARD.route}/$slug") }
            )
        }
    }

}