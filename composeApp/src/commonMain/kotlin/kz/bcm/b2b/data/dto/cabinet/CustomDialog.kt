package kz.bcm.b2b.data.dto.cabinet

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.AnimationVector
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material.Icon
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Delete
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ProvidedValue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.inter_medium
import kz.bcm.b2b.data.dto.bill.BillBodyDto
import kz.bcm.b2b.domain.data.bill.BillBody
import kz.bcm.b2b.presentation.other.theme.ColorDarkRed
import kz.bcm.b2b.presentation.other.theme.ColorMainGreen
import kz.bcm.b2b.presentation.other.theme.ColorPlatinum
import org.jetbrains.compose.resources.Font
import org.jetbrains.compose.resources.painterResource


@Composable
fun CustomDialog(
    body: BillBody?,
    setShowDialog: (Boolean) -> Unit,
    setValue: (BillBody) -> Unit
) {

    var statebody by remember {
        mutableStateOf<BillBodyDto?>(body as BillBodyDto?)
    }



    var stateError by remember {
        mutableStateOf<String?>(null)
    }

    Dialog(
        onDismissRequest = { setShowDialog(false) }
    ) {

        Column(
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(ColorPlatinum)
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(12.dp)
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = body?.code ?: "Создать БИЛ",
                    fontSize = 16.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_medium))
                )

                Icon(
                    imageVector = Icons.Default.Clear,
                    contentDescription = "x",
                    modifier = Modifier
                        .size(30.dp)
                        .clickable {
                            setShowDialog(false)
                        }
                )
            }

            DialogCharacterItem(
                title = "БИК",
                value = statebody?.code ?: "",
                setValue = {
                    statebody = statebody?.copy(code = it) ?: BillBodyDto(code = it)
                }
            )

            DialogCharacterItem(
                title = "ИИК",
                value = statebody?.kbe ?: "",
                setValue = {
                    statebody = statebody?.copy(kbe = it) ?: BillBodyDto(kbe = it)
                }
            )

            DialogCharacterItem(
                title = "Банк",
                value = statebody?.bank ?: "",
                setValue = {
                    statebody = statebody?.copy(bank = it) ?: BillBodyDto(bank = it)
                }
            )

            Text(
                text = if (body != null) "Обновить" else "Создать",
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(Res.font.inter_medium)),
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(6.dp))
                    .background(ColorMainGreen)
                    .clickable {
                        stateError = when {
                            statebody?.code.isNullOrEmpty() -> "Заполните БИК"
                            statebody?.kbe.isNullOrEmpty() -> "Заполните ИИК"
                            statebody?.bank.isNullOrEmpty() -> "Заполните Банк"
                            else -> {
                                setValue(statebody!!)
                                setShowDialog(false)

                                 null
                            }
                        }
                    }
                    .padding(vertical = 12.dp)
            )

            stateError?.let { mess ->
                Text(
                    text = mess,
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_medium)),
                    color = ColorDarkRed
                )
            }

        }
    }

}


@Composable
private fun DialogCharacterItem(
    title: String,
    value: String,
    setValue: (String) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(Res.font.inter_medium))
        )

        BasicTextField(
            value = value,
            onValueChange = setValue,
            modifier = Modifier
                .fillMaxWidth()
                .border(
                    width = 1.dp,
                    color = Color.LightGray,
                    shape = RoundedCornerShape(6.dp)
                )
                .clip(RoundedCornerShape(6.dp))
                .background(Color.White)
                .padding(10.dp)
        )
    }
}




