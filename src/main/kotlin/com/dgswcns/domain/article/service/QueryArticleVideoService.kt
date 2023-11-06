package com.dgswcns.domain.article.service

import com.dgswcns.domain.article.handler.dto.response.ArticleVideoResponse
import com.dgswcns.domain.article.persistence.dao.ArticleRepository
import org.springframework.stereotype.Service

@Service
class QueryArticleVideoService(
    private val articleRepository: ArticleRepository
) {
    suspend fun execute(id: String): ArticleVideoResponse {
        val article = articleRepository.findById(id) ?: throw RuntimeException()
        return ArticleVideoResponse(
            article.video.url
        )
    }
}