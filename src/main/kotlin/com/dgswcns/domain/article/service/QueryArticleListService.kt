package com.dgswcns.domain.article.service

import com.dgswcns.domain.article.handler.dto.response.ArticleBodyResponse
import com.dgswcns.domain.article.handler.dto.response.ArticleResponse
import com.dgswcns.domain.article.handler.dto.response.ArticleSimpleResponse
import com.dgswcns.domain.article.persistence.dao.ArticleRepository
import com.dgswcns.domain.article.persistence.dao.CustomArticleRepository
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import org.springframework.stereotype.Service

@Service
class QueryArticleListService(
    private val articleRepository: ArticleRepository,
    private val customArticleRepository: CustomArticleRepository
) {
    suspend fun execute(page: String): List<ArticleSimpleResponse> {
        return customArticleRepository.findAllWithPaging(page)
            .map {ArticleSimpleResponse(
                it.id,
                it.keyword,
                it.body.thumbnail.url
            ) }.toList()
    }
}