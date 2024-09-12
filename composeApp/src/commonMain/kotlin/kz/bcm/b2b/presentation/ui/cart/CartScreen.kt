package kz.bcm.b2b.presentation.ui.cart

import androidx.compose.foundation.layout.Column
import androidx.compose.material.Text
import androidx.compose.runtime.Composable


@Composable
fun CartScreen() {

    Column {
        Text("Корзина")

        ProductItemInCart()
    }
}