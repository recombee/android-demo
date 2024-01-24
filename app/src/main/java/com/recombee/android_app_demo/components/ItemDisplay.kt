package com.recombee.android_app_demo.components

import android.net.Uri
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.recombee.android_app_demo.data.Item

private fun getGlideModel(url: String): Uri {
    return Uri.parse(url).buildUpon().scheme("https").build()
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun ItemDisplay(item: Item, modifier: Modifier = Modifier) {
    Row(
        modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp, vertical = 12.dp)
            .height(IntrinsicSize.Min),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        if (item.images.isNotEmpty()) {
            GlideImage(
                model = getGlideModel(item.images.first()),
                contentDescription = item.title,
                modifier = Modifier
                    .fillMaxHeight()
                    .aspectRatio(2f / 3f)
                    .clip(RoundedCornerShape(8.dp)),
                alignment = Alignment.CenterStart,
            )
        }
        Column(
            Modifier
                .padding(horizontal = 4.dp)
                .fillMaxWidth()
        ) {
            Text(
                item.title,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(bottom = 4.dp)
            )
            Text(item.description, maxLines = 3)
        }
    }
}

@Preview
@Composable
fun ItemDisplayPreview() {
    ItemDisplay(
        Item(
            itemId = "1",
            title = "Title",
            description = "Description",
            images = listOf("https://picsum.photos/200/300")
        )
    )
}