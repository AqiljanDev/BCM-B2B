package kz.bcm.b2b.presentation.ui.main

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import org.jetbrains.compose.ui.tooling.preview.Preview


@Preview
@Composable
fun DrawerContent() {

    LazyColumn {
        items(10) {
            Text(text = "Item #${it + 1}", fontSize = 15.sp, modifier = Modifier.padding(10.dp))
        }
    }
}