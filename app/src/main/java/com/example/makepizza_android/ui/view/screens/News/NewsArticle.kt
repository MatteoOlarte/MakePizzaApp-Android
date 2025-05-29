package com.example.makepizza_android.domain.models

data class NewsArticle(
    val title: String,
    val description: String?,
    val url: String,
    val image: String?,
    val publishedAt: String,
    val sourceName: String
)