package com.recombee.android_app_demo.item

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recombee.android_app_demo.RouteArgs
import com.recombee.android_app_demo.UserSettings
import com.recombee.android_app_demo.data.Data
import com.recombee.android_app_demo.data.Item
import com.recombee.apiclientkotlin.RecombeeClient
import com.recombee.apiclientkotlin.requests.RecommendItemsToItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ItemsToItemState(val items: List<Item>, val userId: String, val recommId: String?)

@HiltViewModel
class ItemsToItemViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val client: RecombeeClient,
    private val userSettings: DataStore<UserSettings>,
) : ViewModel() {
    private val itemId: String = checkNotNull(savedStateHandle[RouteArgs.ID])

    private val _state = MutableStateFlow<Data<ItemsToItemState>?>(null)
    val state: StateFlow<Data<ItemsToItemState>?> = _state.asStateFlow()

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    init {
        getItems()
    }

    fun getItems() {
        viewModelScope.launch {
            _isLoading.emit(true)
            val userId = userSettings.data.first().userId
            val response = client.sendAsync(
                RecommendItemsToItem(
                    itemId = itemId,
                    targetUserId = userId,
                    count = 10,
                    scenario = "related-assets",
                    returnProperties = true,
                )
            )
            Log.i("ItemsToItemViewModel", "getItems: response=$response")
            if (response.isFailure) {
                _state.emit(
                    Data.Error(
                        response.exceptionOrNull() ?: Exception("Unknown error")
                    )
                )
                _isLoading.emit(false)
                return@launch
            }
            val items = response.getOrNull()?.recomms?.map {
                Item(it)
            } ?: emptyList()
            Log.i("ItemsToItemViewModel", "getItems: items=$items")
            _state.emit(
                Data.Success(
                    ItemsToItemState(
                        items,
                        userId,
                        response.getOrNull()?.recommId
                    )
                )
            )
            _isLoading.emit(false)
        }
    }
}