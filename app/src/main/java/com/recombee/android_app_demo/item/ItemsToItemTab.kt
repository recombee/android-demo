package com.recombee.android_app_demo.item

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.recombee.android_app_demo.NavigationActions
import com.recombee.android_app_demo.components.ErrorMessage
import com.recombee.android_app_demo.components.ItemDisplay
import com.recombee.android_app_demo.data.Data

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemsToItemTab(
    itemId: String,
    navActions: NavigationActions,
    viewModel: ItemsToItemViewModel =
        hiltViewModel<ItemsToItemViewModel, ItemsToItemViewModel.Factory>(
            creationCallback = { factory -> factory.create(itemId) }
        ),
) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    PullToRefreshBox(
        onRefresh = { viewModel.getItems() },
        isRefreshing = isLoading and (uiState != null),
    ) {
        when (uiState) {
            null -> {
                Box(contentAlignment = Alignment.Center, modifier = Modifier.fillMaxSize()) {
                    CircularProgressIndicator(
                        modifier = Modifier.width(64.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        trackColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                }
            }

            is Data.Success -> {
                val state = remember { (uiState as Data.Success).data }
                LazyColumn(Modifier.fillMaxSize()) {
                    item(contentType = "header") {
                        Text(
                            "Related Movies",
                            Modifier.padding(
                                top = 16.dp,
                                start = 16.dp,
                                end = 16.dp,
                                bottom = 8.dp,
                            ),
                            style = MaterialTheme.typography.headlineSmall,
                        )
                    }
                    items(
                        items = state.items,
                        key = { item -> item.itemId },
                        contentType = { "item" },
                    ) {
                        ItemDisplay(
                            it,
                            modifier =
                                Modifier.clickable {
                                    navActions.navigateToItem(it.itemId, state.recommId)
                                },
                        )
                    }
                    item(contentType = "footer") {
                        Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
                    }
                }
            }

            is Data.Error -> {
                ErrorMessage()
            }
        }
    }
}
