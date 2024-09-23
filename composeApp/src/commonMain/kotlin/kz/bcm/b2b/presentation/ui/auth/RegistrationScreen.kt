package kz.bcm.b2b.presentation.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
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
import bcm_b2b.composeapp.generated.resources.inter_regular
import kotlinx.coroutines.launch
import kz.bcm.b2b.data.dto.auth.register.PostRegistrationDto
import kz.bcm.b2b.di.NavigationState
import kz.bcm.b2b.di.NavigationStateHolder
import kz.bcm.b2b.presentation.other.data.Route
import kz.bcm.b2b.presentation.other.data.State
import kz.bcm.b2b.presentation.other.theme.ColorDarkRed
import kz.bcm.b2b.presentation.other.theme.ColorMainGreen
import kz.bcm.b2b.presentation.other.theme.ColorWhiteSmoke
import kz.bcm.b2b.presentation.ui.cart.DropDownMain
import kz.bcm.b2b.presentation.viewmodel.RegistrationViewModel
import kz.bcm.b2b.sharedPref.URL
import kz.bcm.b2b.sharedPref.putStringSharedPref
import org.jetbrains.compose.resources.Font
import org.koin.compose.koinInject


@Composable
fun RegistrationScreen(navController: NavController) {
    val viewModel: RegistrationViewModel = koinInject()
    val state = viewModel.state.collectAsState()
    val coroutine = rememberCoroutineScope()

    val listTypeCompany = listOf("ТОО", "ИП", "АО")
    val scroll = rememberScrollState()

    var stateRegistration by remember {
        mutableStateOf(PostRegistrationDto())
    }

    var stateError by remember {
        mutableStateOf<String?>("email must be an email")
    }

    val selectTypeItem = remember {
        mutableStateOf(listTypeCompany[0])
    }




    when (state.value) {
        is State.Loading -> {
            stateError = null
        }

        is State.Success -> {
            putStringSharedPref(
                key = URL.TOKEN.key,
                value = (state.value as State.Success<String>).data
            )
            navController.navigate(Route.BEING_TESTED.route)

            stateError = null
        }

        is State.Error -> {

            stateError = (state.value as State.Error).message
        }
    }




    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 16.dp, vertical = 20.dp)
            .verticalScroll(scroll),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Регистрация",
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
            Text(
                text = "Уже есть аккаунт?",
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(Res.font.inter_medium))
            )

            Text(
                text = "Войти",
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(Res.font.inter_medium)),
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.6f)
                    .clip(RoundedCornerShape(7.dp))
                    .background(ColorMainGreen)
                    .padding(vertical = 12.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        navController.popBackStack()
                    }
            )


            Spacer(modifier = Modifier.height(5.dp))

            Text(
                text = "Форма B2B партнера",
                fontSize = 24.sp,
                fontFamily = FontFamily(Font(Res.font.inter_medium))
            )

            CustomOutlinedTextField(
                title = "E-Mail",
                value = stateRegistration.email,
                setValue = { stateRegistration = stateRegistration.copy(email = it) },
                keyboardType = KeyboardType.Email
            )

            CustomOutlinedTextField(
                title = "Телефон",
                value = stateRegistration.phone,
                setValue = { stateRegistration = stateRegistration.copy(phone = it) },
                keyboardType = KeyboardType.Phone
            )

            CustomOutlinedTextField(
                title = "Компания",
                value = stateRegistration.company,
                setValue = { stateRegistration = stateRegistration.copy(company = it) }
            )


            Text(
                text = "Тип компании",
                fontSize = 16.sp,
                fontFamily = FontFamily(Font(Res.font.inter_bold))
            )

            DropDownMain(
                items = listTypeCompany,
                selectedItem = selectTypeItem
            )


            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Дополнительная информация",
                fontSize = 20.sp,
                textAlign = TextAlign.Center,
                fontFamily = FontFamily(Font(Res.font.inter_bold))
            )

            CustomOutlinedTextField(
                title = "Сфера деятельности",
                value = stateRegistration.area,
                setValue = { stateRegistration = stateRegistration.copy(area = it) }
            )

            CustomOutlinedTextField(
                title = "Сайт компании",
                value = stateRegistration.site,
                setValue = { stateRegistration = stateRegistration.copy(site = it) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Данные для договора",
                fontSize = 20.sp,
                fontFamily = FontFamily(Font(Res.font.inter_bold))
            )

            CustomOutlinedTextField(
                title = "БИН",
                value = stateRegistration.bin,
                setValue = { stateRegistration = stateRegistration.copy(bin = it) }
            )

            CustomOutlinedTextField(
                title = "Юридический адрес",
                value = stateRegistration.address,
                setValue = { stateRegistration = stateRegistration.copy(address = it) }
            )

            CustomOutlinedTextField(
                title = "БИК",
                value = stateRegistration.bik,
                setValue = { stateRegistration = stateRegistration.copy(bik = it) }
            )

            CustomOutlinedTextField(
                title = "Банк",
                value = stateRegistration.bank,
                setValue = { stateRegistration = stateRegistration.copy(bank = it) }
            )

            CustomOutlinedTextField(
                title = "ИИК (Номер счета)",
                value = stateRegistration.iik,
                setValue = { stateRegistration = stateRegistration.copy(iik = it) }
            )

            Spacer(modifier = Modifier.height(10.dp))

            Text(
                text = "Создать аккаунт",
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(Res.font.inter_medium)),
                color = Color.White,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth(0.7f)
                    .clip(RoundedCornerShape(7.dp))
                    .background(ColorMainGreen)
                    .padding(vertical = 12.dp)
                    .clickable(
                        indication = null,
                        interactionSource = remember { MutableInteractionSource() }
                    ) {
                        viewModel.registration(stateRegistration)
                    }
            )

            stateError?.let {
                Text(
                    text = it,
                    fontSize = 11.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_medium)),
                    color = ColorDarkRed
                )
            }
        }
    }
}