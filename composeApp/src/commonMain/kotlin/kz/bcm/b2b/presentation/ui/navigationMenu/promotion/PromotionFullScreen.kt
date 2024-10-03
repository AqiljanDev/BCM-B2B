package kz.bcm.b2b.presentation.ui.navigationMenu.promotion

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
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
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.inter_bold
import bcm_b2b.composeapp.generated.resources.inter_medium
import bcm_b2b.composeapp.generated.resources.oswald_bold
import kotlinx.coroutines.delay
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kz.bcm.b2b.presentation.viewmodel.PromotionFullViewModel
import org.jetbrains.compose.resources.Font
import org.koin.compose.koinInject


@Composable
fun PromotionFullScreen(
    slug: String?
) {
    val viewModel: PromotionFullViewModel = koinInject()

    val stateSale = viewModel.sale.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getSale(slug.orEmpty())
    }

    Column(
        modifier = Modifier.fillMaxWidth().padding(8.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {

        Text(
            text = stateSale.value.title,
            fontSize = 25.sp,
            fontFamily = FontFamily(Font(Res.font.inter_bold))
        )

        Text(
            text = stateSale.value.text,
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(Res.font.inter_medium)),
            modifier = Modifier.fillMaxWidth().padding(horizontal = 100.dp)
        )

        val date =
            stateSale.value.dateEnd.ifEmpty { "2024-01-01T01:01" }

        CountdownTimer(date)
    }
}


@Composable
fun CountdownTimer(targetDateTime: String) {
    val targetTime = LocalDateTime.parse(targetDateTime)

    var timeLeft by remember { mutableStateOf(calculateTimeLeft(targetTime)) }

    LaunchedEffect(Unit, targetTime, timeLeft) {
        while (timeLeft > 0) {
            delay(1000) // Задержка в 1 секунду
            timeLeft = calculateTimeLeft(targetTime)
        }
    }

    // Расчет дней, часов, минут и секунд
    val days = timeLeft / (24 * 3600)
    val hours = (timeLeft % (24 * 3600)) / 3600
    val minutes = (timeLeft % 3600) / 60
    val seconds = timeLeft % 60

    Column(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = if (timeLeft > 0) "${days}д : ${hours}ч : ${minutes}м : ${seconds}с"
                else "Время истекло!",
            fontSize = 19.sp,
            fontFamily = FontFamily(Font(Res.font.oswald_bold))
        )
    }
}

fun calculateTimeLeft(targetTime: LocalDateTime): Long {
    // Получение текущего времени в нужной временной зоне
    val currentTime = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())

    // Преобразуем LocalDateTime в Instant
    val targetInstant = targetTime.toInstant(TimeZone.currentSystemDefault())
    val currentInstant = currentTime.toInstant(TimeZone.currentSystemDefault())

    // Возвращаем разницу в секундах
    return targetInstant.epochSeconds - currentInstant.epochSeconds // Возвращает оставшееся время в секундах
}

