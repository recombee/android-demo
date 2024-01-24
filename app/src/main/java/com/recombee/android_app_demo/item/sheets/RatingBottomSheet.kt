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

@Composable
fun RatingBottomSheet(onSend: (rating: Double) -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Text(
            text = "Rating",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )

        var rating by remember { mutableFloatStateOf(0f) }
        Slider(
            modifier = Modifier.semantics { contentDescription = "Localized Description" },
            value = rating,
            onValueChange = { rating = it },
            valueRange = -1f..1f,
        )

        Text(text = "Rating: $rating", modifier = Modifier.fillMaxWidth())

        Button(onClick = { onSend(rating.toDouble()) }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Send")
        }
    }
}