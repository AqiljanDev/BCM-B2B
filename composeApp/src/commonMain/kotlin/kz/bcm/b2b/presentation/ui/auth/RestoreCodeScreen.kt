package kz.bcm.b2b.presentation.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.inter_bold
import bcm_b2b.composeapp.generated.resources.inter_medium
import kz.bcm.b2b.presentation.other.data.Route
import kz.bcm.b2b.presentation.other.theme.ColorMainGreen
import kz.bcm.b2b.presentation.other.theme.ColorWhiteSmoke
import org.jetbrains.compose.resources.Font


@Composable
fun RestoreCodeScreen(navController: NavController) {

    var stateEmail by remember {
        mutableStateOf("")
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Восстановление пароля",
            fontSize = 29.sp,
            fontFamily = FontFamily(Font(Res.font.inter_bold))
        )

        Spacer(modifier = Modifier.height(24.dp))

        Column(
            modifier = Modifier.fillMaxWidth()
                .clip(RoundedCornerShape(12.dp))
                .background(ColorWhiteSmoke)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {

            CustomOutlinedTextField(
                title = "E-Mail адрес",
                value = stateEmail,
                setValue = { stateEmail = it },
                keyboardType = KeyboardType.Email,
                icon = Icons.Filled.Email
            )

            Text(
                text = "Получить код",
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(Res.font.inter_medium)),
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
                    .clip(RoundedCornerShape(7.dp))
                    .background(ColorMainGreen)
                    .padding(vertical = 12.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        println("Send code")
                    }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Вернуться",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_bold)),
                    color = Color.Black
                )

                Text(
                    text = "Назад",
                    fontSize = 14.sp,
                    color = ColorMainGreen,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                        .clip(RoundedCornerShape(7.dp))
                        .border(
                            width = 1.dp,
                            color = ColorMainGreen,
                            shape = RoundedCornerShape(7.dp)
                        )
                        .background(Color.White)
                        .padding(vertical = 12.dp)
                        .clickable(
                            indication = null,
                            interactionSource = remember { MutableInteractionSource() }
                        ) {
                            navController.popBackStack()
                        }
                )
            }
        }

    }
}