package com.recombee.android_app_demo.main

import android.util.Log
import androidx.datastore.core.DataStore
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.recombee.android_app_demo.UserSettings
import com.recombee.android_app_demo.data.Data
import com.recombee.android_app_demo.data.Item
import com.recombee.apiclientkotlin.RecombeeClient
import com.recombee.apiclientkotlin.requests.RecommendItemsToUser
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MainScreenState(val items: List<Item>, val userId: String, val recommId: String?)

@HiltViewModel
class MainViewModel @Inject constructor(
    private val client: RecombeeClient,
    private val userSettings: DataStore<UserSettings>,
) : ViewModel() {
    private val _state = MutableStateFlow<Data<MainScreenState>?>(null)
    val state: StateFlow<Data<MainScreenState>?> = _state.asStateFlow()

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
                RecommendItemsToUser(
                    userId = userId,
                    count = 10,
                    scenario = "homepage-top-for-you",
                    returnProperties = true,
                )
            )
            Log.i("MainViewModel", "getItems: success=${response.isSuccess}")
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
            Log.i("MainViewModel", "getItems: items=${items.map { it.title }}")
            _state.emit(
                Data.Success(
                    MainScreenState(
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