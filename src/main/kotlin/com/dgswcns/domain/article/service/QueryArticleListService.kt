package com.dgswcns.domain.article.service

import com.dgswcns.domain.article.handler.dto.response.ArticleSimpleResponse
import com.dgswcns.domain.article.persistence.dao.ArticleRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class QueryArticleListService(
    private val articleRepository: ArticleRepository
) {
    suspend fun execute(): List<ArticleSimpleResponse> {
        return articleRepository.findAll().map {
            ArticleSimpleResponse(
                it.id,
                it.keyword,
                it.body.thumbnail.url
            )
        }.toList()
    }
}