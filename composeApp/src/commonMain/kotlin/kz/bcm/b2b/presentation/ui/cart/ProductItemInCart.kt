package kz.bcm.b2b.presentation.ui.cart

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.kamel.image.KamelImage
import io.kamel.image.asyncPainterResource
import kz.bcm.b2b.presentation.other.theme.Url


@Composable
fun ProductItemInCart() {

    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(15.dp)
    ) {

        Box(
            modifier = Modifier.fillMaxWidth(0.2f),
            contentAlignment = Alignment.Center
        ) {
            KamelImage(
                resource = asyncPainterResource(Url.SRC_IMAGE + ""),
                contentDescription = null
            )
        }


        Column {

        }
    }
}