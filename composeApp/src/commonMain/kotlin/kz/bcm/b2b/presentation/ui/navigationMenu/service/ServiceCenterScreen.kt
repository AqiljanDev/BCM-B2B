package kz.bcm.b2b.presentation.ui.navigationMenu.service

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.inter_bold
import bcm_b2b.composeapp.generated.resources.inter_medium
import org.jetbrains.compose.resources.Font


@Composable
fun ServiceCenterScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {

        Text(
            text = "Сервисный центр",
            fontSize = 25.sp,
            fontFamily = FontFamily(Font(Res.font.inter_bold))
        )

        Text(
            text = "Магазин БиСиЭМ гарантирует высокое качество своей продукции и стремится обеспечить максимальную удовлетворенность клиентов. " +
                    "Мы предоставляем гарантию на все приобретенные товары, чтобы обеспечить вас долговременной надежностью и беззаботным использованием.\n\n" +
                    "Если у вас возникнут вопросы или неисправности, пожалуйста, не стесняйтесь обращаться к нам. Вы можете привести продукцию в наш магазин, " +
                    "где наши квалифицированные специалисты окажут вам необходимую поддержку.\n\n" +
                    "Кроме того, мы предоставляем удобную опцию обращения в специализированные сервисные центры, где профессионалы смогут провести диагностику " +
                    "и ремонт вашего изделия. Это позволяет вам выбирать наиболее удобный вариант обслуживания в зависимости от вашего местоположения и распорядка дня.\n\n" +
                    "Наши гарантийные условия разработаны для вашего уверенного использования продукции, и мы готовы сделать все возможное для того, чтобы в случае необходимости" +
                    " ваш опыт с нашей продукцией оставался приятным и беззаботным.\n\n" +
                    "В случае необходимости гарантийного обслуживания, помимо возможности посетить наш магазин, вы также можете обратиться в следующие сервисные центры:",
            fontSize = 13.sp,
            fontFamily = FontFamily(Font(Res.font.inter_medium))
        )

        colText(
            f = "Сервисный центр ingco:",
            l = "Адрес: Немировича Данченко 18а (ridincome)",
            t = "Контактный телефон: 8747 647 8171"
        )

        colText(
            f = "Сервисный центр bosch:",
            l = "Адрес: Щепеткова 117 (СтройИндустрия-kz)",
            t = "Контактный телефон: 8777 571 3494"
        )
    }
}


@Composable
private fun colText(f: String, l: String, t: String) {
    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(
            text = f,
            fontSize = 13.sp,
            fontFamily = FontFamily(Font(Res.font.inter_bold)),
            color = Color.Black
        )

        Text(
            text = l,
            fontSize = 13.sp,
            fontFamily = FontFamily(Font(Res.font.inter_medium))
        )

        Text(
            text = t,
            fontSize = 13.sp,
            fontFamily = FontFamily(Font(Res.font.inter_medium))
        )
    }
}