package com.recombee.android_app_demo.item.sheets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.recombee.android_app_demo.components.NumberField

@Composable
fun CartAdditionBottomSheet(onSend: (amount: Double?, price: Double?) -> Unit) {
    var amount by remember { mutableStateOf<Double?>(null) }
    var price by remember { mutableStateOf<Double?>(null) }

    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Text(
            text = "Cart Addition",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
        Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
            NumberField(
                value = amount,
                onValueChange = { amount = it?.toDouble() },
                label = "Amount",
                modifier = Modifier.fillMaxWidth(),
            )
            NumberField(
                value = price,
                onValueChange = { price = it?.toDouble() },
                label = "Price",
                modifier = Modifier.fillMaxWidth(),
            )
        }
        Button(onClick = { onSend(amount, price) }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Send")
        }
    }
}
