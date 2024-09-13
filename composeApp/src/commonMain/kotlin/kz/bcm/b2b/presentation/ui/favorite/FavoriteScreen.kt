package kz.bcm.b2b.presentation.ui.favorite

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.inter_bold
import kz.bcm.b2b.presentation.other.data.Route
import kz.bcm.b2b.presentation.ui.catalog.ProductItem
import kz.bcm.b2b.presentation.viewmodel.FavoriteViewModel
import org.jetbrains.compose.resources.Font
import org.koin.compose.koinInject


@Composable
fun FavoriteScreen(navController: NavController) {
    val viewModel: FavoriteViewModel = koinInject()

    val stateProducts = viewModel.productsFeatured.collectAsState()

    val stateFavorite = viewModel.favorite.collectAsState()
    val stateCompare = viewModel.compare.collectAsState()
    val stateCart = viewModel.cart.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getProducts()
        viewModel.initializeData()
    }

    LaunchedEffect(stateFavorite) {
        println("Favorite screen = stateFavorite: ${stateFavorite.value}")
    }

    LaunchedEffect(stateProducts) {
        println("Favorite screen = stateProducts: ${stateProducts.value}")
    }


    LazyColumn(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp),
        contentPadding = PaddingValues(vertical = 10.dp)
    ) {
        item {
            Text(
                "Избранное",
                fontSize = 25.sp,
                fontFamily = FontFamily(Font(Res.font.inter_bold))
            )
        }

        items(items = stateProducts.value, key = { it.id }) { prod ->

            ProductItem(
                product = prod.product,
                compareList = stateCompare.value,
                favoriteList = stateFavorite.value,
                cartList = stateCart.value.products,
                clickFavorite = { prodId ->
                    println("Favorite screen = click favorite")
                    viewModel.eventFavorite(prodId)
                },
                clickCompare = { prodId -> viewModel.eventCompare(prodId) },
                clickCart = { item, id -> viewModel.eventCart(item, id) },
                clickRoot = { slug -> navController.navigate("${Route.CARD.route}/$slug") }
            )
        }

    }
}