package com.example.youtubeapi.models

import com.example.youtubeapi.models.Item
import com.example.youtubeapi.models.PageInfo

data class YoutubeData(
    val etag: String,
    val items: List<Item>,
    val kind: String,
    val nextPageToken: String,
    val pageInfo: PageInfo,
    val regionCode: String
)