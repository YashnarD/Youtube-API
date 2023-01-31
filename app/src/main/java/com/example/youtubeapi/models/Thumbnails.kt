package com.example.youtubeapi.models

import com.example.youtubeapi.models.Default
import com.example.youtubeapi.models.High
import com.example.youtubeapi.models.Medium

data class Thumbnails(
    val default: Default,
    val high: High,
    val medium: Medium
)