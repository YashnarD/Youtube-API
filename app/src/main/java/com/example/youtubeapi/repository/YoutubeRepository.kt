package com.example.youtubeapi.repository

import com.example.youtubeapi.retrofit.ApiService
import kotlinx.coroutines.flow.flow

class YoutubeRepository(private val apiService: ApiService) {
    suspend fun getVideos() = flow { emit(apiService.getChannelsVideos()) }
}