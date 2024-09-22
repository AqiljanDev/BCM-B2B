package kz.bcm.b2b.presentation.ui.auth

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kz.bcm.b2b.presentation.other.theme.ColorMainGreen
import kz.bcm.b2b.presentation.other.theme.ColorMainOrange


@Composable
fun CustomOutlinedTextField(
    title: String,
    value: String,
    setValue: (String) -> Unit,
    keyboardType: KeyboardType? = null,
    icon: ImageVector? = null
) {

    OutlinedTextField(
        value = value,
        onValueChange = setValue,
        label = {
            Text(text = title)
        },
        singleLine = true,
        keyboardActions = KeyboardActions(
            onSearch = {
                println("CLICK SEARCH")
            }
        ),
        keyboardOptions = if (keyboardType != null) {
            KeyboardOptions(
                keyboardType = keyboardType,
                imeAction = ImeAction.Next
            )
        } else {
            KeyboardOptions.Default.copy(imeAction = ImeAction.Next)
        },
        shape = RoundedCornerShape(8.dp),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedLabelColor = ColorMainOrange,
            unfocusedLabelColor = Color.Gray,
            focusedBorderColor = ColorMainOrange,
            unfocusedBorderColor = Color.Gray,
            cursorColor = ColorMainGreen
        ),
        leadingIcon = icon?.let {
            {
                Icon(
                    imageVector = it,
                    contentDescription = "email"
                )
            }
        },
        modifier = Modifier.fillMaxWidth()
    )


}