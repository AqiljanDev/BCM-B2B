package kz.bcm.b2b.presentation.ui.profile

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.AlertDialog
import androidx.compose.material.Snackbar
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.nestedscroll.NestedScrollConnection
import androidx.compose.ui.input.nestedscroll.NestedScrollSource
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import androidx.compose.ui.unit.sp
import androidx.compose.ui.window.Dialog
import androidx.lifecycle.viewmodel.compose.viewModel
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.inter_bold
import bcm_b2b.composeapp.generated.resources.inter_medium
import bcm_b2b.composeapp.generated.resources.inter_regular
import kz.bcm.b2b.data.dto.bill.BillBodyDto
import kz.bcm.b2b.data.dto.cabinet.CabinetDto
import kz.bcm.b2b.data.dto.cabinet.CustomDialog
import kz.bcm.b2b.domain.data.bill.BillBody
import kz.bcm.b2b.domain.data.bill.BillMy
import kz.bcm.b2b.domain.data.cabinet.Cabinet
import kz.bcm.b2b.presentation.other.theme.ColorDarkRed
import kz.bcm.b2b.presentation.other.theme.ColorMainGreen
import kz.bcm.b2b.presentation.other.theme.ColorMunsell
import kz.bcm.b2b.presentation.other.theme.ColorPlatinum
import org.jetbrains.compose.resources.Font


@Composable
fun DataItem(
    cabinet: Cabinet,
    bills: List<BillMy>,
    updateCabinet: (cabinet: Cabinet) -> Unit,
    createBill: (body: BillBody) -> Unit,
    updateBill: (id: Int, body: BillBody) -> Unit,
    deleteBill: (id: Int) -> Unit
) {

    val stateBillBody = remember {
        mutableStateOf<BillBody?>(null)
    }

    var stateCabinet by remember {
        mutableStateOf<CabinetDto>(cabinet as CabinetDto)
    }

    val showDialog = remember {
        mutableStateOf(false)
    }
    val showAlertDialog = remember {
        mutableStateOf(false)
    }

    val outScroll = rememberScrollState()


    val stateCurrId = remember {
        mutableStateOf(0)
    }

    Column(
        modifier = Modifier.fillMaxWidth()
            .padding(10.dp)
            .verticalScroll(outScroll),
        verticalArrangement = Arrangement.spacedBy(10.dp)
    ) {

        AnimatedVisibility(visible = showDialog.value) {
            CustomDialog(
                body = stateBillBody.value,
                setShowDialog = { showDialog.value = it },
                setValue = { bill ->
                    println("DataItem: bill: $bill, stateBillBody: ${stateBillBody.value}, id: ${stateCurrId.value}")

                    if (stateBillBody.value == null) {
                        stateBillBody.value = bill
                        createBill(bill)
                    } else {
                        updateBill(stateCurrId.value, bill)
                    }

                }
            )
        }

        AnimatedVisibility(visible = showAlertDialog.value) {
            AlertDialog(
                onDismissRequest = { showAlertDialog.value = false },
                buttons = {
                    Row(
                        modifier = Modifier.fillMaxWidth().padding(20.dp),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Нет",
                            fontSize = 13.sp,
                            fontFamily = FontFamily(Font(Res.font.inter_bold)),
                            color = Color.White,
                            modifier = Modifier.clip(RoundedCornerShape(4.dp))
                                .background(ColorDarkRed)
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                .clickable {
                                    showAlertDialog.value = false
                                }
                        )

                        Text(
                            text = "Да",
                            fontSize = 13.sp,
                            fontFamily = FontFamily(Font(Res.font.inter_bold)),
                            color = Color.White,
                            modifier = Modifier.clip(RoundedCornerShape(4.dp))
                                .background(ColorMainGreen)
                                .padding(horizontal = 12.dp, vertical = 6.dp)
                                .clickable {
                                    deleteBill(stateCurrId.value)

                                    showAlertDialog.value = false
                                }
                        )
                    }
                },
                title = { Text(
                    text = "Вы уверены, что хотите удалить?",
                    fontSize = 13.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_medium)),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                ) },
                modifier = Modifier.clip(RoundedCornerShape(8.dp))
            )


        }

        Text(
            text = "Данные",
            fontSize = 25.sp,
            fontFamily = FontFamily(Font(Res.font.inter_bold))
        )

        Box(modifier = Modifier.fillMaxWidth().height(1.dp).background(ColorPlatinum))

        Text(
            text = "Создать P/C",
            fontSize = 15.sp,
            fontFamily = FontFamily(Font(Res.font.inter_regular)),
            color = Color.White,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .background(ColorMainGreen)
                .padding(horizontal = 18.dp, vertical = 7.dp)
                .clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    stateBillBody.value = null
                    showDialog.value = true
                }
        )

        LazyColumn(
            modifier = Modifier
                .heightIn(max = 20_000.dp)
                .nestedScroll(
                    connection = object : NestedScrollConnection {
                        override fun onPreScroll(
                            available: Offset,
                            source: NestedScrollSource
                        ): Offset {
                            if (outScroll.canScrollForward && available.y < 0) {
                                val consumer = outScroll.dispatchRawDelta(-available.y)
                                return Offset(x = 0f, y = -consumer)
                            }
                            return Offset.Zero
                        }
                    }),
            verticalArrangement = Arrangement.spacedBy(8.dp)
        ) {
            items(bills) { bill ->

                BillItem(
                    bill = bill,
                    updateBill = { id, bill, show ->
                        stateCurrId.value = id
                        stateBillBody.value = bill
                        showDialog.value = show
                    },
                    deleteBill = {
                        stateCurrId.value = it
                        showAlertDialog.value = true
                    }
                )
            }
        }

        Column(
            modifier = Modifier.clip(RoundedCornerShape(8.dp))
                .background(ColorPlatinum)
                .padding(10.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            CabinetCharacterItem(
                title = "Номер телефона",
                value = stateCabinet.phone,
                onValueChange = { newValue ->
                    stateCabinet = stateCabinet.copy(phone = newValue)
                }
            )

            CabinetCharacterItem(
                title = "Имя компании",
                value = stateCabinet.company,
                onValueChange = { newValue ->
                    stateCabinet = stateCabinet.copy(company = newValue)
                }
            )

            CabinetCharacterItem(
                title = "Тип",
                value = stateCabinet.type,
                onValueChange = { newValue ->
                    stateCabinet = stateCabinet.copy(type = newValue)
                }
            )

            CabinetCharacterItem(
                title = "Сфера деятельности",
                value = stateCabinet.area,
                onValueChange = { newValue ->
                    stateCabinet = stateCabinet.copy(area = newValue)
                }
            )

            CabinetCharacterItem(
                title = "Сайт компании",
                value = stateCabinet.site,
                onValueChange = { newValue ->
                    stateCabinet = stateCabinet.copy(site = newValue)
                }
            )

            CabinetCharacterItem(
                title = "БИН",
                value = stateCabinet.bin,
                onValueChange = { newValue ->
                    stateCabinet = stateCabinet.copy(bin = newValue)
                }
            )

            CabinetCharacterItem(
                title = "Юридический адрес",
                value = stateCabinet.address,
                onValueChange = { newValue ->
                    stateCabinet = stateCabinet.copy(address = newValue)
                }
            )

            CabinetCharacterItem(
                title = "БИК",
                value = stateCabinet.bik,
                onValueChange = { newValue ->
                    stateCabinet = stateCabinet.copy(bik = newValue)
                }
            )

            CabinetCharacterItem(
                title = "Банк",
                value = stateCabinet.bank,
                onValueChange = { newValue ->
                    stateCabinet = stateCabinet.copy(bank = newValue)
                }
            )

            CabinetCharacterItem(
                title = "ИИК (Номер счета)",
                value = stateCabinet.iik,
                onValueChange = { newValue ->
                    stateCabinet = stateCabinet.copy(iik = newValue)
                }
            )

            Text(
                text = "Сохранить",
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(Res.font.inter_medium)),
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(vertical = 10.dp)
                    .fillMaxWidth(0.6f)
                    .clip(RoundedCornerShape(8.dp))
                    .background(ColorMainGreen)
                    .clickable {
                        updateCabinet(stateCabinet)
                    }
                    .padding(vertical = 10.dp)
            )
        }

    }

}

@Composable
fun BillItem(
    bill: BillMy,
    updateBill: (id: Int, body: BillBody, showState: Boolean) -> Unit,
    deleteBill: (id: Int) -> Unit
) {
    Column(
        modifier = Modifier.clip(RoundedCornerShape(9.dp))
            .background(ColorPlatinum)
            .padding(14.dp),
        verticalArrangement = Arrangement.spacedBy(2.dp)
    ) {
        BillCharacterItem("БИК", bill.code)
        BillCharacterItem("ИИК", bill.kbe)
        BillCharacterItem("Банк", bill.bank)

        Spacer(modifier = Modifier.height(10.dp))

        Row(
            modifier = Modifier,
            horizontalArrangement = Arrangement.spacedBy(14.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = "Удалить",
                fontSize = 11.sp,
                fontFamily = FontFamily(Font(Res.font.inter_medium)),
                color = Color.White,
                modifier = Modifier.clip(RoundedCornerShape(6.dp))
                    .background(ColorMainGreen)
                    .padding(7.dp)
                    .clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource()
                    ) {
                        deleteBill(bill.id)
                    }
            )

            Text(
                text = "Редактировать",
                fontSize = 11.sp,
                fontFamily = FontFamily(Font(Res.font.inter_medium)),
                color = Color.White,
                modifier = Modifier.clip(RoundedCornerShape(6.dp))
                    .background(ColorMainGreen)
                    .padding(7.dp)
                    .clickable(
                        indication = null,
                        interactionSource = MutableInteractionSource()
                    ) {
                        val body = BillBodyDto(
                            code = bill.code,
                            bank = bill.bank,
                            kbe = bill.kbe
                        )

                        updateBill(
                            bill.id,
                            body,
                            true
                        )
                    }
            )
        }
    }
}


@Composable
private fun BillCharacterItem(
    name: String,
    value: String
) {
    Row(
        horizontalArrangement = Arrangement.spacedBy(4.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = "${name}:",
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(Res.font.inter_medium)),
            color = Color.Gray
        )

        Text(
            text = value,
            fontSize = 12.sp,
            fontFamily = FontFamily(Font(Res.font.inter_medium))
        )
    }
}


@Composable
fun CabinetCharacterItem(
    title: String,
    value: String,
    onValueChange: (String) -> Unit
) {

    Column(
        modifier = Modifier.fillMaxWidth(),
        verticalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        Text(
            text = title,
            fontSize = 13.sp,
            fontFamily = FontFamily(Font(Res.font.inter_medium))
        )

        BasicTextField(
            value = value,
            onValueChange = onValueChange,
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









