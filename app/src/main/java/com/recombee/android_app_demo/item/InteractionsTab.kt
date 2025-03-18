package com.recombee.android_app_demo.item

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.recombee.android_app_demo.item.sheets.BookmarkBottomSheet
import com.recombee.android_app_demo.item.sheets.CartAdditionBottomSheet
import com.recombee.android_app_demo.item.sheets.DetailViewBottomSheet
import com.recombee.android_app_demo.item.sheets.PurchaseBottomSheet
import com.recombee.android_app_demo.item.sheets.RatingBottomSheet
import com.recombee.android_app_demo.item.sheets.ViewPortionBottomSheet

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InteractionsTab(
    showSnackbar: (String, () -> Unit) -> Unit,
    itemId: String,
    recommId: String?,
    viewModel: InteractionsViewModel = hiltViewModel(),
) {
    val bottomSheetState by viewModel.bottomSheetState.collectAsStateWithLifecycle()
    val sheetState = rememberModalBottomSheetState(skipPartiallyExpanded = false)
    val notificationState by viewModel.notificationState.collectAsStateWithLifecycle()

    LaunchedEffect(notificationState) {
        val message =
            when (notificationState) {
                Notification.NONE -> return@LaunchedEffect
                Notification.BOOKMARK_SUCCESS -> "Bookmark sent"
                Notification.CART_ADDITION_SUCCESS -> "Cart addition sent"
                Notification.DETAIL_VIEW_SUCCESS -> "Detail view sent"
                Notification.PURCHASE_SUCCESS -> "Purchase sent"
                Notification.RATING_SUCCESS -> "Rating sent"
                Notification.VIEW_PORTION_SUCCESS -> "View portion sent"
                Notification.ERROR -> "An error occurred"
            }
        showSnackbar(message) { viewModel.dismissNotification() }
    }

    Column(
        Modifier.fillMaxSize().padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        Text(
            text = "User-Item Interactions",
            style = MaterialTheme.typography.headlineSmall,
            modifier = Modifier.padding(bottom = 8.dp),
        )
        listOf(
                BottomSheetState.BOOKMARK to "Bookmark",
                BottomSheetState.CART_ADDITION to "Cart Addition",
                BottomSheetState.DETAIL_VIEW to "Detail View",
                BottomSheetState.PURCHASE to "Purchase",
                BottomSheetState.RATING to "Rating",
                BottomSheetState.VIEW_PORTION to "View Portion",
            )
            .map {
                val (sheetType, text) = it
                FilledTonalButton(onClick = { viewModel.showBottomSheet(sheetType) }) {
                    Text(text = text)
                }
            }
        Text(text = "Recommendation ID: $recommId")
    }

    if (bottomSheetState != BottomSheetState.NONE) {
        ModalBottomSheet(
            onDismissRequest = { viewModel.hideBottomSheet() },
            sheetState = sheetState,
            contentWindowInsets = { WindowInsets(0.dp) },
        ) {
            Box(Modifier.padding(16.dp)) {
                when (bottomSheetState) {
                    BottomSheetState.BOOKMARK ->
                        BookmarkBottomSheet(
                            onSend = {
                                viewModel.sendBookmark(itemId, recommId)
                                viewModel.hideBottomSheet()
                            }
                        )

                    BottomSheetState.CART_ADDITION ->
                        CartAdditionBottomSheet(
                            onSend = { amount, price ->
                                viewModel.sendCartAddition(itemId, amount, price, recommId)
                                viewModel.hideBottomSheet()
                            }
                        )

                    BottomSheetState.DETAIL_VIEW ->
                        DetailViewBottomSheet(
                            onSend = { duration ->
                                viewModel.sendDetailView(itemId, duration, recommId)
                                viewModel.hideBottomSheet()
                            }
                        )

                    BottomSheetState.PURCHASE ->
                        PurchaseBottomSheet(
                            onSend = { amount, price, profit ->
                                viewModel.sendPurchase(itemId, amount, price, profit, recommId)
                                viewModel.hideBottomSheet()
                            }
                        )

                    BottomSheetState.RATING ->
                        RatingBottomSheet(
                            onSend = { rating ->
                                viewModel.sendRating(itemId, rating, recommId)
                                viewModel.hideBottomSheet()
                            }
                        )

                    BottomSheetState.VIEW_PORTION ->
                        ViewPortionBottomSheet(
                            onSend = { portion ->
                                viewModel.sendViewPortion(itemId, portion, recommId)
                                viewModel.hideBottomSheet()
                            }
                        )

                    else -> Unit
                }
            }
            Spacer(Modifier.windowInsetsBottomHeight(WindowInsets.systemBars))
        }
    }
}
