package com.dgswcns.domain.article.service

import com.dgswcns.domain.article.handler.dto.response.ArticleSimpleResponse
import com.dgswcns.domain.article.persistence.dao.CustomArticleRepository
import org.springframework.stereotype.Service
import java.text.SimpleDateFormat

@Service
class QueryArticleListService(
    private val customArticleRepository: CustomArticleRepository
) {
    val format = SimpleDateFormat("yyyy-MM-dd")
    suspend fun execute(
        page: String,
        date: String?
    ): List<ArticleSimpleResponse> {
        return customArticleRepository.findAllByDateWithPaging(page, date)
            .map {ArticleSimpleResponse(
                it.id,
                it.keyword,
                it.body.thumbnail.url,
                format.format(it.createAt)
            ) }.toList()
    }
}