package com.dgswcns.thirdparty.news.webclient.dto.response

data class NewsResponse (
    val status: String,
    val totalResults: String,
    val results: List<Result>
) {
    data class Result(
        val article_id: String,
        val title: String,
        val link: String,
        val keywords: List<String>?,
        val creator: List<String>?,
        val video_url: String?,
        val description: String,
        val content: String,
        val pubDate: String,
        val image_url: String,
        val source_id: String,
        val source_priority: String,
        val country: List<String>,
        val category: List<String>,
        val language: String
    )
}