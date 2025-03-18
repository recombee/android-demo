package com.recombee.android_app_demo.item.sheets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Slider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kotlin.math.roundToInt

@Composable
fun ViewPortionBottomSheet(onSend: (portion: Double) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Text(
            text = "View Portion",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )

        var portion by remember { mutableFloatStateOf(0f) }
        Slider(
            modifier = Modifier.semantics { contentDescription = "Localized Description" },
            value = portion,
            onValueChange = { portion = (it * 100f).roundToInt() / 100f },
            valueRange = 0f..1f,
            steps = 49,
        )

        Text(text = "Portion: $portion", modifier = Modifier.fillMaxWidth())

        Button(onClick = { onSend(portion.toDouble()) }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Send")
        }
    }
}
