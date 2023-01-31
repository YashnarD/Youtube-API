package com.example.youtubeapi.models

data class Item(
    val etag: String,
    val id: Id,
    val kind: String,
    val snippet: Snippet
)