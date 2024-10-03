package kz.bcm.b2b.presentation.ui.navigationMenu.promotion

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.inter_bold
import bcm_b2b.composeapp.generated.resources.inter_medium
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kz.bcm.b2b.presentation.other.data.Route
import kz.bcm.b2b.presentation.other.theme.Url
import kz.bcm.b2b.presentation.viewmodel.PromotionViewModel
import org.jetbrains.compose.resources.Font
import org.koin.compose.koinInject


@Composable
fun PromotionScreen(navController: NavController) {
    val viewModel: PromotionViewModel = koinInject()

    val stateSale = viewModel.sale.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.getSale()
    }


    LazyColumn(
        modifier = Modifier.fillMaxWidth().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(16.dp)
    ) {

        item {
            Text(
                text = "Акции",
                fontSize = 25.sp,
                fontFamily = FontFamily(Font(Res.font.inter_bold))
            )
        }


        if (stateSale.value.isNotEmpty()) {
            items(stateSale.value) { sale ->
                val painter = asyncPainterResource(Url.SRC_IMAGE_SALES + sale.photo)

                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(8.dp)
                ) {
                    KamelImage(
                        resource = painter,
                        contentDescription = null,
                        modifier = Modifier.fillMaxWidth()
                            .clip(RoundedCornerShape(8.dp))
                            .aspectRatio(2f)
                            .clickable {
                                navController.navigate("${Route.PROMOTION_FULL.route}/${sale.slug}")
                            }
                    )

                    Text(
                        text = sale.title,
                        fontSize = 13.sp,
                        fontFamily = FontFamily(Font(Res.font.inter_medium))
                    )
                }
            }

        } else {

            item {
                Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                    Text(
                        text = "Акций нет",
                        fontSize = 21.sp,
                        fontFamily = FontFamily(Font(Res.font.inter_medium))
                    )
                }
            }
        }
    }
}
