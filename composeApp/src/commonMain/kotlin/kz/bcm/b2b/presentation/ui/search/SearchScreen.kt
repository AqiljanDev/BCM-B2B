package kz.bcm.b2b.presentation.ui.search

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.ui.Modifier
import androidx.navigation.NavController


@Composable
fun SearchScreen(
    navController: NavController,
    fieldState: MutableState<String>
) {

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        Text(text = "Searh screen = ${fieldState.value}")
    }
}