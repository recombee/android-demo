package com.recombee.android_app_demo.item

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recombee.android_app_demo.UserSettings
import com.recombee.apiclientkotlin.RecombeeClient
import com.recombee.apiclientkotlin.requests.AddBookmark
import com.recombee.apiclientkotlin.requests.AddCartAddition
import com.recombee.apiclientkotlin.requests.AddDetailView
import com.recombee.apiclientkotlin.requests.AddPurchase
import com.recombee.apiclientkotlin.requests.AddRating
import com.recombee.apiclientkotlin.requests.Request
import com.recombee.apiclientkotlin.requests.SetViewPortion
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import java.time.Instant
import javax.inject.Inject

enum class BottomSheetState {
    NONE,
    BOOKMARK,
    CART_ADDITION,
    DETAIL_VIEW,
    PURCHASE,
    RATING,
    VIEW_PORTION,
}

enum class Notification {
    NONE,
    BOOKMARK_SUCCESS,
    CART_ADDITION_SUCCESS,
    DETAIL_VIEW_SUCCESS,
    PURCHASE_SUCCESS,
    RATING_SUCCESS,
    VIEW_PORTION_SUCCESS,
    ERROR,
}

@HiltViewModel
class InteractionsViewModel
@Inject
constructor(private val client: RecombeeClient, private val userSettings: DataStore<UserSettings>) :
    ViewModel() {
    private val _bottomSheetState = MutableStateFlow(BottomSheetState.NONE)
    val bottomSheetState = _bottomSheetState.asStateFlow()

    private val _notificationState = MutableStateFlow(Notification.NONE)
    val notificationState = _notificationState.asStateFlow()

    private suspend fun getUserId(): String {
        return userSettings.data.first().userId
    }

    fun showBottomSheet(state: BottomSheetState) {
        _bottomSheetState.value = state
    }

    fun hideBottomSheet() {
        _bottomSheetState.value = BottomSheetState.NONE
    }

    fun sendBookmark(itemId: String, recommId: String?) {
        viewModelScope.launch {
            sendInteraction(
                AddBookmark(
                    userId = getUserId(),
                    itemId = itemId,
                    timestamp = Instant.now(),
                    recommId = recommId,
                ),
                Notification.BOOKMARK_SUCCESS,
            )
        }
    }

    fun sendCartAddition(itemId: String, amount: Double?, price: Double?, recommId: String?) {
        viewModelScope.launch {
            sendInteraction(
                AddCartAddition(
                    userId = getUserId(),
                    itemId = itemId,
                    timestamp = Instant.now(),
                    amount = amount,
                    price = price,
                    recommId = recommId,
                ),
                Notification.CART_ADDITION_SUCCESS,
            )
        }
    }

    fun sendDetailView(itemId: String, duration: Long?, recommId: String?) {
        viewModelScope.launch {
            sendInteraction(
                AddDetailView(
                    userId = getUserId(),
                    itemId = itemId,
                    timestamp = Instant.now(),
                    duration = duration,
                    recommId = recommId,
                ),
                Notification.DETAIL_VIEW_SUCCESS,
            )
        }
    }

    fun sendPurchase(
        itemId: String,
        amount: Double?,
        price: Double?,
        profit: Double?,
        recommId: String?,
    ) {
        viewModelScope.launch {
            sendInteraction(
                AddPurchase(
                    userId = getUserId(),
                    itemId = itemId,
                    timestamp = Instant.now(),
                    amount = amount,
                    price = price,
                    profit = profit,
                    recommId = recommId,
                ),
                Notification.PURCHASE_SUCCESS,
            )
        }
    }

    fun sendRating(itemId: String, rating: Double, recommId: String?) {
        viewModelScope.launch {
            sendInteraction(
                AddRating(
                    userId = getUserId(),
                    itemId = itemId,
                    timestamp = Instant.now(),
                    rating = rating,
                    recommId = recommId,
                ),
                Notification.RATING_SUCCESS,
            )
        }
    }

    fun sendViewPortion(itemId: String, portion: Double, recommId: String?) {
        viewModelScope.launch {
            sendInteraction(
                SetViewPortion(
                    userId = getUserId(),
                    itemId = itemId,
                    timestamp = Instant.now(),
                    portion = portion,
                    recommId = recommId,
                ),
                Notification.VIEW_PORTION_SUCCESS,
            )
        }
    }

    fun dismissNotification() {
        _notificationState.value = Notification.NONE
    }

    private suspend inline fun <reified T> sendInteraction(
        request: Request<T>,
        successNotification: Notification,
    ) {
        val response = client.sendAsync(request)
        Log.i("InteractionsViewModel", "sendInteraction: success=${response.isSuccess}")
        if (response.isFailure) {
            Log.e(
                "InteractionsViewModel",
                "sendInteraction: ${response.exceptionOrNull()?.toString() ?: "Unknown error"}",
            )
            _notificationState.value = Notification.ERROR
            return
        }
        _notificationState.value = successNotification
    }
}
