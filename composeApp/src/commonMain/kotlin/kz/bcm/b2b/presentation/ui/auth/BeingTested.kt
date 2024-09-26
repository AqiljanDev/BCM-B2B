package kz.bcm.b2b.presentation.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import androidx.navigation.NavController
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.inter_bold
import bcm_b2b.composeapp.generated.resources.inter_medium
import bcm_b2b.composeapp.generated.resources.inter_regular
import kotlinx.coroutines.delay
import kz.bcm.b2b.presentation.other.data.Route
import kz.bcm.b2b.presentation.other.theme.ColorMainGreen
import kz.bcm.b2b.presentation.other.theme.ColorMainOrange
import org.jetbrains.compose.resources.Font


@Composable
fun BeingTested(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        LoadingDotsAnimation()

        Spacer(modifier = Modifier.height(15.dp))

        Text(
            text = "Проверить статус",
            fontSize = 14.sp,
            fontFamily = FontFamily(Font(Res.font.inter_medium)),
            color = Color.White,
            modifier = Modifier.clip(RoundedCornerShape(12.dp))
                .background(ColorMainGreen)
                .clickable {  }
                .padding(horizontal = 16.dp, vertical = 10.dp)
        )

        Spacer(modifier = Modifier.height(30.dp))

        Text(
            text = "Зайти другим аккаунтом",
            fontSize = 13.sp,
            fontFamily = FontFamily(Font(Res.font.inter_regular)),
            color = Color.Gray,
            modifier = Modifier.clickable(
                indication = null,
                interactionSource = remember { MutableInteractionSource() }
            ) {
                navController.popBackStack(Route.LOGIN.route, inclusive = false)
            }
        )

    }
}

@Composable
private fun LoadingDotsAnimation() {
    var dotCount by remember { mutableStateOf(0) }

    // Запускаем бесконечный цикл для изменения количества точек
    LaunchedEffect(Unit) {
        while (true) {
            dotCount = (dotCount + 1) % 4 // Модифицируем количество точек от 0 до 3
            delay(500) // Задержка для анимации
        }
    }

    // Отображение текста с точками
    Text(
        text = "Вы на проверке${".".repeat(dotCount)}",
        fontSize = 20.sp,
        fontFamily = FontFamily(Font(Res.font.inter_bold)),
        color = Color.Black
    )
}