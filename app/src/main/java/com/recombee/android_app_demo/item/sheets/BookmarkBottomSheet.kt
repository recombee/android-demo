package com.recombee.android_app_demo.item.sheets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp

@Composable
fun BookmarkBottomSheet(onSend: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(24.dp)) {
        Text(
            text = "Bookmark",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
        )
        Button(onClick = onSend, modifier = Modifier.fillMaxWidth()) {
            Text(text = "Send")
        }
    }
}