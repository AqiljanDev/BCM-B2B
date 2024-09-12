package kz.bcm.b2b.presentation.ui.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.inter_bold
import bcm_b2b.composeapp.generated.resources.inter_medium
import bcm_b2b.composeapp.generated.resources.inter_regular
import kz.bcm.b2b.domain.data.findOneCatalog.ParentCategory
import kz.bcm.b2b.presentation.other.data.Route
import kz.bcm.b2b.presentation.other.theme.ColorMainGreen
import kz.bcm.b2b.presentation.other.theme.ColorPlatinum
import kz.bcm.b2b.presentation.viewmodel.CatalogViewModel
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.ui.tooling.preview.Preview
import org.koin.compose.koinInject
import org.koin.core.definition.indexKey


@OptIn(ExperimentalLayoutApi::class)
@Preview
@Composable
fun CatalogScreen(navController: NavHostController, slug: String? = null) {
    val viewModel: CatalogViewModel = koinInject()
    val listState = rememberLazyListState()

    val stateCatalog = viewModel.catalog.collectAsState()
    val statePage = viewModel.page.collectAsState()

    val stateCompare = viewModel.compare.collectAsState()
    val stateFavorite = viewModel.favorite.collectAsState()
    val stateCart = viewModel.cart.collectAsState()

    var currentCategory by remember {
        mutableStateOf(slug ?: "index")
    }
    var currentPager by remember {
        mutableStateOf(1)
    }

    LaunchedEffect(Unit, currentCategory, currentPager) {
        viewModel.getFindOne(category = currentCategory, page = currentPager)

        listState.scrollToItem(0)
    }

    LaunchedEffect(Unit) {
        viewModel.initializeData()
    }

    LaunchedEffect(Unit, currentCategory) {
        viewModel.getPage(category = currentCategory)
    }

    LazyColumn(
        modifier = Modifier.padding(10.dp),
        verticalArrangement = Arrangement.spacedBy(10.dp),
        state = listState
    ) {

        item {
            Breadcrumbs(
                stateCatalog.value.info.parentCategory
            ) { slugStr ->
                currentCategory = slugStr
            }
        }

        item {
            TitleAndFilter(stateCatalog.value.info.title) {
                println("Click filter")
            }
        }

        item {
            var expanded by remember { mutableStateOf(false) }
            val childCategories = stateCatalog.value.info.childCategory
            val listChild = if (expanded) childCategories else childCategories.take(4)

            FlowRow(
                modifier = Modifier.fillMaxWidth()
            ) {
                listChild.forEach { item ->
                    Text(
                        text = item.title,
                        fontFamily = FontFamily(Font(Res.font.inter_regular)),
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(ColorPlatinum)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember {
                                    MutableInteractionSource()
                                }
                            ) {
                                currentCategory = item.slug
                            }
                    )
                }

                if (childCategories.size > 4) {
                    Text(
                        text = if (expanded) "Скрыть" else "Раскрыть",
                        fontFamily = FontFamily(Font(Res.font.inter_regular)),
                        color = Color.White,
                        modifier = Modifier
                            .padding(4.dp)
                            .clip(RoundedCornerShape(8.dp))
                            .background(ColorMainGreen)
                            .padding(horizontal = 10.dp, vertical = 4.dp)
                            .clickable(
                                indication = null,
                                interactionSource = remember { MutableInteractionSource() }
                            ) {
                                expanded = !expanded
                            }
                    )
                }
            }
        }

        items(stateCatalog.value.products) { product ->
            ProductItem(
                product = product,
                compareList = stateCompare.value,
                favoriteList = stateFavorite.value,
                cartList = stateCart.value.products,
                clickFavorite = { prodId -> viewModel.eventFavorite(prodId) },
                clickCompare = { prodId -> viewModel.eventCompare(prodId) },
                clickCart = { item, id -> viewModel.eventCart(item, id) },
                clickRoot = { slug -> navController.navigate("${Route.CARD.name}/$slug") }
            )
        }

        if (statePage.value > 1) {
            item {
                Pagination(
                    currentPage = currentPager,
                    totalPages = statePage.value
                ) { page -> currentPager = page }
            }
        }
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun Breadcrumbs(
    parentCategory: ParentCategory?,
    onClick: (slug: String) -> Unit
) {
    val list = mutableListOf<Pair<String, String>>()
    var parent = parentCategory

    println("parent category = $parentCategory")

    while (parent != null) {
        list.add(parent.title to parent.slug)
        parent = parent.parentCategory
    }

    println("list: $list")
    FlowRow(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(3.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        list.reversed().forEachIndexed { index, item ->
            println("list foreach: ${item.first}, $index")

            Text(text = item.first,
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(Res.font.inter_medium)),
                modifier = Modifier.clickable {
                    onClick(item.second)
                }
            )

            if (list.size - 1 > index)
                Text(
                    text = "/",
                    fontSize = 16.sp,
                    color = Color.Gray
                )

        }
    }
}


@Composable
private fun TitleAndFilter(title: String, clickFilter: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = title,
            fontSize = 22.sp,
            fontFamily = FontFamily(Font(Res.font.inter_bold)),
            modifier = Modifier.weight(1f)
        )

        Text(
            text = "Фильтр",
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(Res.font.inter_medium)),
            color = Color.White,
            modifier = Modifier
                .clip(RoundedCornerShape(5.dp))
                .background(ColorMainGreen)
                .padding(horizontal = 17.dp, vertical = 8.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember {
                        MutableInteractionSource()
                    }
                ) { clickFilter() }
        )
    }
}


