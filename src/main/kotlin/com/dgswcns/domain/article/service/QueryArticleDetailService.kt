package com.dgswcns.domain.article.service

import com.dgswcns.domain.article.exception.ArticleExceptions
import com.dgswcns.domain.article.handler.dto.response.ArticleBodyResponse
import com.dgswcns.domain.article.handler.dto.response.ArticleResponse
import com.dgswcns.domain.article.persistence.dao.ArticleRepository
import org.springframework.stereotype.Service

@Service
class QueryArticleDetailService(
    private val articleRepository: ArticleRepository
) {
    suspend fun getArticleById(
        id: String
    ): ArticleResponse {
        val article = articleRepository.findById(id) ?: throw ArticleExceptions.NotFoundArticle()
        return ArticleResponse(
            article.keyword,
            ArticleBodyResponse(
                article.body.title,
                article.body.content,
                article.body.thumbnail.url
            )
        )
    }
}