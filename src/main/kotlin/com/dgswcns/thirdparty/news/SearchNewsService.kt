package com.dgswcns.thirdparty.news

import com.dgswcns.domain.article.handler.dto.response.ArticleBodyResponse
import com.dgswcns.domain.article.handler.dto.response.ArticleResponse
import com.dgswcns.thirdparty.news.exception.NewsExceptions
import com.dgswcns.thirdparty.news.webclient.NewsClient
import org.springframework.stereotype.Service

@Service
class SearchNewsService(
    private val newsClient: NewsClient
) {
    suspend fun searchNewsByKeyword(keyword: String): ArticleResponse {
        val results = newsClient.searchNewsByQ(keyword).results

        if (results.isEmpty()) {
            throw NewsExceptions.NewsNotFoundException()
        }

        return ArticleResponse(
            keyword,
            ArticleBodyResponse(
                results[0].title,
                results[0].content,
                results[0].image_url
            )
        )
    }
}