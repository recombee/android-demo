package com.recombee.android_app_demo.components

import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.recombee.android_app_demo.ui.theme.RecomflixTheme

@Composable
fun NumberField(
    value: Number?,
    modifier: Modifier = Modifier,
    label: String?,
    decimal: Boolean = true,
    onValueChange: (Number?) -> Unit,
) {
    var number by remember { mutableStateOf(value) }
    val textValue = remember {
        number = value
        mutableStateOf(value?.toDouble()?.let {
            if (it == 0.0) {
                ""
            } else if (it % 1.0 == 0.0) {
                it.toInt().toString()
            } else {
                it.toString()
            }
        } ?: "")
    }
    val numberRegex = remember {
        (if (decimal) "-?\\d*[.]?\\d*" else "-?\\d*\\d*").toRegex()
    }

    OutlinedTextField(
        value = textValue.value,
        onValueChange = {
            if (numberRegex.matches(it)) {
                textValue.value = it
                number = it.toDoubleOrNull()
                onValueChange(number)
            }
        },
        label = { if (label != null) Text(label) },
        singleLine = true,
        keyboardOptions = KeyboardOptions(keyboardType = if (decimal) KeyboardType.Decimal else KeyboardType.Number),
        modifier = modifier,
    )
}

@Preview
@Composable
fun NumberFieldPreview() {
    RecomflixTheme {
        NumberField(0.0, label = "Test") {}
    }
}