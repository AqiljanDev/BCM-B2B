package kz.bcm.b2b.presentation.ui.navigationMenu.promotion

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.inter_bold
import bcm_b2b.composeapp.generated.resources.inter_medium
import org.jetbrains.compose.resources.Font


@Composable
fun PromotionScreen() {
    Column(
        modifier = Modifier.fillMaxWidth().padding(13.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Акции",
            fontSize = 25.sp,
            fontFamily = FontFamily(Font(Res.font.inter_bold))
        )

        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            Text(
                text = "Акций нет",
                fontSize = 21.sp,
                fontFamily = FontFamily(Font(Res.font.inter_medium))
            )
        }
    }
}