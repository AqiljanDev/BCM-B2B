package kz.bcm.b2b.presentation.ui.navigationMenu.delivery

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.inter_bold
import bcm_b2b.composeapp.generated.resources.inter_medium
import org.jetbrains.compose.resources.Font


@Composable
fun DeliveryPaymentScreen() {

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Text(
            text = "Доставка и оплата",
            fontSize = 25.sp,
            fontFamily = FontFamily(Font(Res.font.inter_bold))
        )

        Text(
            text = "Доставка",
            fontSize = 21.sp,
            fontFamily = FontFamily(Font(Res.font.inter_bold))
        )

        Text(
            text = "Наша компания гордится предоставлением клиентам в Алматы высококачественных и профессиональных услуг по доставке товаров. " +
                    "Мы стремимся обеспечить надежность и эффективность в каждом этапе процесса доставки, начиная от момента размещения заказа.\n\n" +
                    "Бесплатная доставка предоставляется для заказов на сумму от 70 000 тенге, что является одним из проявлений нашего стремления к " +
                    "удовлетворению потребностей клиентов. Наша служба доставки оперативно и точно выполняет каждый заказ, придерживаясь высоких стандартов обслуживания.\n\n" +
                    "Мы придаем особое значение времени наших клиентов, и поэтому наша цель - сократить сроки доставки до абсолютного минимума, обеспечивая тем самым быстрое " +
                    "и эффективное выполнение заказов. Весь процесс осуществляется в соответствии с современными стандартами и с использованием передовых технологий," +
                    " чтобы обеспечить максимальную прозрачность и безопасность.\n\n" +
                    "Выбирая наши услуги доставки, клиенты могут быть уверены, что каждый заказ будет тщательно упакован и доставлен вовремя, " +
                    "с соблюдением высочайших стандартов сохранности и качества обслуживания.",
            fontSize = 13.sp,
            fontFamily = FontFamily(Font(Res.font.inter_medium)),
            modifier = Modifier.fillMaxWidth()
        )

        Text(
            text = "Гарантия и Оплата",
            fontSize = 20.sp,
            fontFamily = FontFamily(Font(Res.font.inter_bold))
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(6.dp)
        ) {
            Text(
                text = "Магазин БиСиЭМ стремится предоставить клиентам максимальное удобство при выборе способа оплаты для приобретения товаров. " +
                        "Мы предлагаем разнообразные и гибкие варианты оплаты, чтобы удовлетворить различные потребности наших клиентов.\n\n"
            )

            rowText(
                tbord = "Оплата наличными:",
                tMedium = "Для тех, кто предпочитает традиционный метод оплаты, у нас доступна оплата наличными в магазине при получении товара."
            )

            rowText(
                tbord = "Оплата картой:",
                tMedium = "Мы принимаем банковские карты, что обеспечивает быстроту и удобство в процессе совершения покупок."
            )

            rowText(
                tbord = "Перечисление на счёт:",
                tMedium = "Клиенты могут воспользоваться опцией перечисления средств на указанный счет для оплаты своих заказов."
            )

            rowText(
                tbord = "Рассрочка и кредит:",
                tMedium = "Для тех, кто предпочитает распределить оплату на более длительный срок, мы предлагаем гибкие условия рассрочки и кредитования."
            )

            rowText(
                tbord = "Kaspi QR:",
                tMedium = "У нас также доступна современная и удобная опция оплаты через Kaspi QR, что обеспечивает мгновенную и безопасную транзакцию."
            )

            Text(
                text = "Выберите тот способ оплаты, который соответствует вашим предпочтениям и обстоятельствам." +
                        " Мы ценим ваш комфорт и готовы предоставить вам удобные варианты оплаты для приятного и беззаботного покупательского опыта.",
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(Res.font.inter_medium))
            )
        }
    }
}


@Composable
private fun rowText(tbord: String, tMedium: String) {

    Text(
        text = buildAnnotatedString {
            withStyle(style = SpanStyle(fontFamily = FontFamily(Font(Res.font.inter_bold)))) {
                append(tbord)
            }

            append(" ")

            withStyle(style = SpanStyle(fontFamily = FontFamily(Font(Res.font.inter_medium)))) {
                append(tMedium)
            }
        },
        fontSize = 13.sp
    )
}









