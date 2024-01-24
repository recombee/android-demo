package com.recombee.android_app_demo.components

import android.util.Log
import androidx.compose.foundation.layout.Box
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.pulltorefresh.PullToRefreshContainer
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.zIndex

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PullToRefreshLayout(
    onRefresh: () -> Unit,
    isRefreshing: Boolean,
    modifier: Modifier = Modifier,
    content: @Composable () -> Unit
) {
    val pullToRefreshState = rememberPullToRefreshState()
    var uiRefreshTriggered by remember { mutableStateOf(false) }

    if (pullToRefreshState.isRefreshing) {
        if (isRefreshing) {
            // synced
        } else if (!uiRefreshTriggered) {
            uiRefreshTriggered = true
            Log.i("MainScreen", "Triggered")
            onRefresh()
        } else {
            uiRefreshTriggered = false
            Log.i("MainScreen", "Refreshed")
            pullToRefreshState.endRefresh()
        }
    }

    Box(
        modifier
            .nestedScroll(pullToRefreshState.nestedScrollConnection)
            .zIndex(-1f)) {
        content()
        PullToRefreshContainer(
            modifier = Modifier
                .align(Alignment.TopCenter),
            state = pullToRefreshState,
        )
    }
}