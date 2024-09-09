package kz.bcm.b2b.presentation.ui.catalog

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kz.bcm.b2b.presentation.viewmodel.CatalogViewModel
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject


@Preview
@Composable
fun CatalogScreen() {
    val viewModel: CatalogViewModel = koinInject()

    val stateCatalog = viewModel.catalog.collectAsState()
    val stateCompare = viewModel.compare.collectAsState()
    val stateFavorite = viewModel.favorite.collectAsState()
    val stateCart = viewModel.cart.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }

    LazyColumn(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        items(stateCatalog.value.products) { product ->
            ProductItem(
                product = product,
                compareList = stateCompare.value,
                favoriteList = stateFavorite.value,
                cartList = stateCart.value.products,
                clickFavorite = { prodId -> viewModel.eventFavorite(prodId) },
                clickCompare = { prodId -> viewModel.eventCompare(prodId) },
                clickCart = { item, id -> viewModel.eventCart(item, id) }
            )
        }
    }
}

