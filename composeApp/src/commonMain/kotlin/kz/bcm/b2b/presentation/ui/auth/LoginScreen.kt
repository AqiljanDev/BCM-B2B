package kz.bcm.b2b.presentation.ui.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Lock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import org.jetbrains.compose.resources.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import bcm_b2b.composeapp.generated.resources.Res
import bcm_b2b.composeapp.generated.resources.inter_bold
import bcm_b2b.composeapp.generated.resources.inter_medium
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import kz.bcm.b2b.data.dto.auth.login.PostLoginDto
import kz.bcm.b2b.di.NavigationState
import kz.bcm.b2b.di.NavigationStateHolder
import kz.bcm.b2b.presentation.other.data.Route
import kz.bcm.b2b.presentation.other.data.State
import kz.bcm.b2b.presentation.other.theme.ColorDarkRed
import kz.bcm.b2b.presentation.other.theme.ColorMainGreen
import kz.bcm.b2b.presentation.other.theme.ColorWhiteSmoke
import kz.bcm.b2b.presentation.viewmodel.LoginViewModel
import kz.bcm.b2b.sharedPref.URL
import kz.bcm.b2b.sharedPref.putStringSharedPref
import org.koin.compose.koinInject


@Composable
fun LoginScreen(navController: NavController) {
    val viewModel: LoginViewModel = koinInject()
    val coroutine = rememberCoroutineScope()

    val state = viewModel.state.collectAsState()

    var stateLogin by remember {
        mutableStateOf(PostLoginDto())
    }

    var stateError by remember {
        mutableStateOf<String?>("Пароль не верный")
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

            stateError = null

            coroutine.launch {
                NavigationStateHolder.navigationState.emit(NavigationState.Normal)
            }
        }

        is State.Error -> {

            stateError = (state.value as State.Error).message
        }

        else -> {

        }
    }

    Column(
        modifier = Modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Text(
            text = "Вход",
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
                value = stateLogin.email,
                setValue = { stateLogin = stateLogin.copy(email = it) },
                keyboardType = KeyboardType.Email,
                icon = Icons.Filled.Email
            )

            CustomOutlinedTextField(
                title = "Пароль",
                value = stateLogin.password,
                setValue = { stateLogin = stateLogin.copy(password = it) },
                keyboardType = KeyboardType.Password,
                icon = Icons.Filled.Lock
            )

            Text(
                text = "Восстановление пароля",
                fontSize = 13.sp,
                fontFamily = FontFamily(Font(Res.font.inter_medium)),
                color = ColorMainGreen,
                modifier = Modifier.clickable(
                    indication = null,
                    interactionSource = remember { MutableInteractionSource() }
                ) {
                    navController.navigate(Route.RESTORE_CODE.route)
                }
            )

            Spacer(modifier = Modifier.height(2.dp))

            Text(
                text = "Войти",
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
                        viewModel.postLogin(stateLogin)
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

            Spacer(modifier = Modifier.height(15.dp))

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                Text(
                    text = "Стать дилером",
                    fontSize = 20.sp,
                    fontFamily = FontFamily(Font(Res.font.inter_bold)),
                    color = Color.Black
                )

                Text(
                    text = "Регистрация",
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
                            navController.navigate(Route.REGISTRATION.route)
                        }
                )
            }
        }
    }

}






