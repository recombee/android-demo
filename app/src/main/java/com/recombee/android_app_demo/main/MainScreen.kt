package com.recombee.android_app_demo.main

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material.icons.outlined.Info
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.pulltorefresh.PullToRefreshBox
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.recombee.android_app_demo.NavigationActions
import com.recombee.android_app_demo.components.ErrorMessage
import com.recombee.android_app_demo.components.ItemDisplay
import com.recombee.android_app_demo.data.Data
import com.recombee.android_app_demo.data.Item

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen(navActions: NavigationActions, viewModel: MainViewModel = hiltViewModel()) {
    val uiState by viewModel.state.collectAsStateWithLifecycle()
    val isLoading by viewModel.isLoading.collectAsStateWithLifecycle()

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ), title = {
                Text("Recombee Android Demo")
            }, actions = {
                IconButton(onClick = { navActions.navigateToOnboarding() }) {
                    Icon(
                        imageVector = Icons.Outlined.Info, contentDescription = "Info"
                    )
                }
                IconButton(onClick = { navActions.navigateToSettings() }) {
                    Icon(
                        imageVector = Icons.Filled.Settings, contentDescription = "User"
                    )
                }
            })
        },
        contentWindowInsets = WindowInsets(0.dp),
    ) { innerPadding ->
        PullToRefreshBox(
            onRefresh = {
                viewModel.getItems()
            },
            isRefreshing = isLoading and (uiState != null),
            modifier = Modifier
                .padding(innerPadding)
                .fillMaxSize(),
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
                    Column(Modifier.fillMaxSize()) {
                        val state = (uiState as Data.Success).data
                        ItemsList(itemList = state.items, onItemClick = { itemId ->
                            navActions.navigateToItem(
                                itemId, state.recommId
                            )
                        })
                    }
                }

                is Data.Error -> {
                    ErrorMessage()
                }
            }
        }
    }
}

@Composable
fun ItemsList(itemList: List<Item>, onItemClick: (itemId: String) -> Unit) {
    LazyColumn {
        item(contentType = "header") {
            Text(
                "Top Picks for You",
                Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp, bottom = 8.dp),
                style = MaterialTheme.typography.headlineSmall
            )
        }
        items(items = itemList, key = { item -> item.itemId }, contentType = { "item" }) {
            ItemDisplay(it, modifier = Modifier.clickable { onItemClick(it.itemId) })
        }
        item(contentType = "footer") {
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun ItemsListPreview() {
    ItemsList(itemList = listOf(Item("id", "title", "desc", listOf("image"))), onItemClick = {})
}
