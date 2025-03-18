package com.recombee.android_app_demo.item

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.zIndex
import com.recombee.android_app_demo.NavigationActions
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ItemScreen(navActions: NavigationActions, itemId: String, recommId: String?) {
    val tabs = remember { listOf("Interactions", "Items to Item") }
    val pagerState = rememberPagerState(pageCount = { tabs.size })

    val scope = rememberCoroutineScope()
    val snackbarHostState = remember { SnackbarHostState() }

    Scaffold(topBar = {
        TopAppBar(
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = MaterialTheme.colorScheme.background,
                titleContentColor = MaterialTheme.colorScheme.primary,
            ), title = {
                Text("Item $itemId")
            }, navigationIcon = {
                IconButton(onClick = { navActions.goBack() }) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, "Back")
                }
            })
    }, contentWindowInsets = WindowInsets(0.dp), snackbarHost = {
        SnackbarHost(hostState = snackbarHostState)
    }) { innerPadding ->
        Column(modifier = Modifier.padding(innerPadding)) {
            PrimaryTabRow(
                selectedTabIndex = pagerState.currentPage
            ) {
                Tab(
                    selected = pagerState.currentPage == 0,
                    onClick = { scope.launch { pagerState.animateScrollToPage(0) } },
                    text = {
                        Text(
                            text = tabs[0], maxLines = 2, overflow = TextOverflow.Ellipsis
                        )
                    })
                Tab(selected = pagerState.currentPage == 1,
                    onClick = { scope.launch { pagerState.animateScrollToPage(1) } }, text = {
                        Text(
                            text = tabs[1], maxLines = 2, overflow = TextOverflow.Ellipsis
                        )
                    })
            }
            HorizontalPager(
                modifier = Modifier.zIndex(-1f),
                state = pagerState,
            ) { selectedTabIndex ->
                when (selectedTabIndex) {
                    0 -> InteractionsTab(
                        showSnackbar = { message, reset ->
                            scope.launch {
                                snackbarHostState.showSnackbar(message)
                                reset()
                            }
                        }, itemId = itemId, recommId = recommId
                    )

                    1 -> ItemsToItemTab(itemId, navActions)
                }
            }
        }
    }
}
