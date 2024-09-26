package kz.bcm.b2b.presentation.ui.navigationMenu.catalogList

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.Icon
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.ic_right_arrow_simple
import bcm_b2b.composeapp.generated.resources.inter_medium
import kz.bcm.b2b.data.dto.categories.ChildCategoryDto
import kz.bcm.b2b.domain.data.categories.ChildCategory
import kz.bcm.b2b.presentation.other.data.Route
import kz.bcm.b2b.presentation.other.theme.ColorWhiteSmoke
import kz.bcm.b2b.presentation.viewmodel.CatalogListViewModel
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.koinInject


@Composable
fun CatalogListScreen(navController: NavController, slug: String?) {
    val viewModel: CatalogListViewModel = koinInject()

    val stateCategories = viewModel.categories.collectAsState()
    var currentList by remember {
        mutableStateOf(stateCategories.value)
    }

    val stateSlug by remember {
        mutableStateOf(slug)
    }

    var selectAll = "index"

    LaunchedEffect(Unit) {
        viewModel.findCategories()
    }

    LaunchedEffect(stateCategories.value) {
        currentList = stateCategories.value.firstOrNull()?.childCategory ?: listOf()
        println("current list: $currentList")
    }


    LazyColumn(
        modifier = Modifier.fillMaxWidth().background(ColorWhiteSmoke),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(1.dp)
    ) {

        items(
            listOf(ChildCategoryDto(slug = selectAll, title = "Все категории")) + currentList
        ) { category ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.White)
                    .clickable(

                    ) {
                        println("catalog list === category.slug: ${category.slug}, select: $selectAll, stateSlug: $stateSlug")
                        if (category.slug == selectAll) {

                            if (stateSlug != null) {
                                navController.previousBackStackEntry?.savedStateHandle?.set(
                                    "slug",
                                    selectAll
                                )
                                println("catalog list --- pop back stack ")
                                navController.popBackStack()
                            } else navController.navigate("${Route.CATALOG.route}/${category.slug}")

                            return@clickable
                        }

                        if (category.childCategory.isNotEmpty()) {
                            selectAll = category.slug
                            currentList = category.childCategory
                        } else {

                            if (stateSlug != null) {
                                navController.previousBackStackEntry?.savedStateHandle?.set(
                                    "slug",
                                    category.slug
                                )

                                println("catalog list --- pop back stack  task 222")
                                navController.popBackStack()
                            } else navController.navigate("${Route.CATALOG.route}/${category.slug}")
                        }
                    },
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Text(
                    text = category.title,
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_medium)),
                    modifier = Modifier.padding(8.dp)
                )

                Icon(
                    painter = painterResource(Res.drawable.ic_right_arrow_simple),
                    contentDescription = "right",
                    modifier = Modifier.padding(8.dp)
                )
            }
        }
    }

}