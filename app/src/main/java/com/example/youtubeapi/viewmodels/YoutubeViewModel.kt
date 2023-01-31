package com.example.youtubeapi.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtubeapi.repository.YoutubeRepository
import com.example.youtubeapi.retrofit.ApiClient
import com.example.youtubeapi.utils.NetworkHelper
import com.example.youtubeapi.utils.YoutubeResource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class YoutubeViewModel(private val networkHelper: NetworkHelper) : ViewModel() {

    private val youtubeRepository = YoutubeRepository(ApiClient.apiService)
    private val stateFlow = MutableStateFlow<YoutubeResource>(YoutubeResource.Loading)

    init {
        getVideos()
    }

    private fun getVideos() {
        if (networkHelper.isNetworkConnected()) {
            viewModelScope.launch {
                youtubeRepository.getVideos().catch {
                    stateFlow.value = YoutubeResource.Error(it.message ?: "")
                }.collect {
                    if (it.isSuccessful && it.body() != null) {
                        stateFlow.value = YoutubeResource.Success(it.body()!!)
                    } else {
                        stateFlow.value = YoutubeResource.Error(it.message())
                    }
                }
            }
        } else {
            stateFlow.value = YoutubeResource.Error("No internet connection")
        }
    }

    fun getStateVideos(): StateFlow<YoutubeResource> {
        return stateFlow
    }

}