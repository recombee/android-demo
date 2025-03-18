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
fun DetailViewBottomSheet(onSend: (duration: Long?) -> Unit) {
    var duration by remember { mutableStateOf<Long?>(null) }

    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Text(
            text = "Detail View",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
        NumberField(
            value = duration,
            onValueChange = { duration = it?.toLong() },
            label = "Duration",
            decimal = false,
            modifier = Modifier.fillMaxWidth(),
        )
        Button(onClick = { onSend(duration) }, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Send")
        }
    }
}
