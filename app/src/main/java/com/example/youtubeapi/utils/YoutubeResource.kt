package com.example.youtubeapi.utils

import com.example.youtubeapi.models.YoutubeData

sealed class YoutubeResource {

    object Loading: YoutubeResource()

    data class Success(val youtubeData: YoutubeData): YoutubeResource()

    data class Error(val message: String): YoutubeResource()
}