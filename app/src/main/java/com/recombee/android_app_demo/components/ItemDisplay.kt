package com.recombee.android_app_demo.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.ColorPainter
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.ColorImage
import coil3.annotation.ExperimentalCoilApi
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePreviewHandler
import coil3.compose.LocalAsyncImagePreviewHandler
import com.recombee.android_app_demo.data.Item

@Composable
fun ItemDisplay(item: Item, modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        if (item.images.isNotEmpty()) {
            AsyncImage(
                model = item.images.first(),
                contentDescription = item.title,
                clipToBounds = true,
                contentScale = ContentScale.FillBounds,
                placeholder = ColorPainter(Color.LightGray),
                modifier = Modifier.width(64.dp).clip(RoundedCornerShape(8.dp)),
                alignment = Alignment.CenterStart,
            )
        }
        Column(Modifier.padding(horizontal = 4.dp).fillMaxWidth()) {
            Text(
                item.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp),
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
            )
            Text(item.description, maxLines = 3, overflow = TextOverflow.Ellipsis)
        }
    }
}

@OptIn(ExperimentalCoilApi::class)
@Preview
@Composable
fun ItemDisplayPreview() {
    val previewHandler = AsyncImagePreviewHandler { ColorImage(Color.LightGray.toArgb(), 200, 300) }
    CompositionLocalProvider(LocalAsyncImagePreviewHandler provides previewHandler) {
        ItemDisplay(
            Item(
                itemId = "1",
                title = "Title",
                description = "Description",
                images = listOf("https://picsum.photos/200/300"),
            )
        )
    }
}
