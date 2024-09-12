package kz.bcm.b2b.presentation.ui.catalog

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TextField
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.ic_double_arrow_right
import bcm_b2b.composeapp.generated.resources.inter_regular
import kz.bcm.b2b.presentation.other.theme.ColorMainGreen
import kz.bcm.b2b.presentation.other.theme.ColorMainOrange
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource

@Composable
fun Pagination(
    currentPage: Int,
    totalPages: Int,
    onPageSelected: (Int) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            val visiblePages = mutableListOf<Int>()

            // Определение видимых страниц
            if (totalPages <= 4) {
                // Если страниц меньше или равно 4, показываем все
                visiblePages.addAll(1..totalPages)
            } else {
                // Если страниц больше 4, показываем страницы с учетом текущей страницы
                val startPage = maxOf(1, currentPage - 1)
                val endPage = minOf(totalPages, currentPage + 1)

                if (startPage > 2) {
                    visiblePages.add(1) // Первая страница
                    visiblePages.add(-1) // Троеточие
                } else if (startPage == 2) {
                    visiblePages.add(1) // Первая страница
                }

                visiblePages.addAll(startPage..endPage)

                if (endPage < totalPages - 1) {
                    visiblePages.add(-1) // Троеточие
                    visiblePages.add(totalPages) // Последняя страница
                } else if (endPage == totalPages - 1) {
                    visiblePages.add(totalPages) // Последняя страница
                }
            }

            // Отображение кнопок страниц
            for (page in visiblePages) {
                if (page == -1) {
                    Text("...", color = ColorMainOrange)
                } else {
                    PageButton(
                        page = page,
                        isSelected = page == currentPage,
                        onClick = { onPageSelected(page) }
                    )
                }
            }
        }
        Spacer(modifier = Modifier.height(10.dp))
        val inputPage = remember { mutableStateOf("") }

        // Поле для ввода страницы
        Row(
            Modifier.background(Color.White),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "Перейти на", color = Color.LightGray)
            Spacer(modifier = Modifier.width(8.dp))
            BasicTextField(
                value = inputPage.value,
                onValueChange = { inputPage.value = it },
                modifier = Modifier
                    .widthIn(max = 100.dp)
                    .border(width = 1.dp, color = Color.LightGray, shape = RoundedCornerShape(4.dp))
                    .background(Color.Transparent)
                    .padding(8.dp),
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                ),
                textStyle = TextStyle(fontFamily = FontFamily(Font(Res.font.inter_regular)))
            )

            Spacer(modifier = Modifier.width(8.dp))

            // Кнопка перехода
            Button(
                onClick = {
                    inputPage.value.toIntOrNull()?.let { page ->
                        if (page in 1..totalPages) onPageSelected(page)
                    }
                },
                modifier = Modifier
                    .background(Color.Transparent)
                    .size(32.dp),
                colors = ButtonDefaults.buttonColors(backgroundColor = ColorMainOrange),
                contentPadding = PaddingValues(0.dp),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_double_arrow_right),
                    contentDescription = "Перейти",
                    tint = Color.White
                )
            }
        }
    }
}

@Composable
fun PageButton(
    page: Int,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    TextButton(
        onClick = onClick,
        shape = CircleShape,
        colors = ButtonDefaults.textButtonColors(
            backgroundColor = if (isSelected) Color(0xFFF5F5F5) else Color.Transparent,
            contentColor = if (isSelected) Color(0xFF532962) else Color(0xFF8E4A8C)
        )
    ) {
        Text(
            text = page.toString(),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(1.dp),
            color = ColorMainOrange
        )
    }
}



