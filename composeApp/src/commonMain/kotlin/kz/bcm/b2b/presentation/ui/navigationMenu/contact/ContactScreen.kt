package kz.bcm.b2b.presentation.ui.navigationMenu.contact

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material.icons.filled.Phone
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.inter_bold
import bcm_b2b.composeapp.generated.resources.inter_medium
import bcm_b2b.composeapp.generated.resources.oswald_medium
import org.jetbrains.compose.resources.Font


@Composable
fun ContactScreen() {
    Column(
        modifier = Modifier.fillMaxSize().padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Text(
            text = "Контакты",
            fontSize = 25.sp,
            fontFamily = FontFamily(Font(Res.font.inter_medium))
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.Phone,
                    contentDescription = "phone"
                )

                Text(
                    text = "Телефоны",
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_bold))
                )
            }

            Text(
                text = "+7 777 777 777",
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(Res.font.oswald_medium))
            )

            Text(
                text = "+7 777 777 777",
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(Res.font.oswald_medium))
            )

            Text(
                text = "+7 777 777 777",
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(Res.font.oswald_medium))
            )
        }

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(3.dp)
        ) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                Icon(
                    imageVector = Icons.Filled.LocationOn,
                    contentDescription = "loaction"
                )

                Text(
                    text = "Адрес",
                    fontSize = 15.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_medium))
                )
            }

            Text(
                text = "address",
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(Res.font.oswald_medium))
            )
        }
    }
}