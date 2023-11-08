package com.dgswcns.domain.article.handler

import com.dgswcns.domain.article.handler.dto.request.CreateArticleRequest
import com.dgswcns.domain.article.service.*
import com.dgswcns.global.error.GlobalExceptions
import com.dgswcns.global.extensions.extractBody
import com.dgswcns.global.extensions.extractPath
import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*

@Component
class ArticleHandler(
    private val createArticleService: CreateArticleService,
    private val queryArticleDetailService: QueryArticleDetailService,
    private val queryArticleListService: QueryArticleListService,
    private val queryArticleVideoService: QueryArticleVideoService
) {
    suspend fun create(request: ServerRequest): ServerResponse {
        createArticleService.createByKeyword(request.extractBody<CreateArticleRequest>().awaitSingle())
        return ServerResponse.status(HttpStatus.CREATED).json().buildAndAwait()
    }

    suspend fun getArticleById(request: ServerRequest): ServerResponse {
        return ServerResponse
            .status(HttpStatus.OK)
            .json()
            .bodyValueAndAwait(
                queryArticleDetailService.getArticleById(
                    request.extractPath("id")
                )
            )
    }

    suspend fun getArticleVideoById(request: ServerRequest): ServerResponse {
        return ServerResponse
            .status(HttpStatus.OK)
            .json()
            .bodyValueAndAwait(
                queryArticleVideoService.execute(request.extractPath("id"))
            )
    }

    suspend fun getArticles(request: ServerRequest): ServerResponse {
        val page = request.queryParamOrNull("page") ?: throw GlobalExceptions.BadRequestError()
        val date = request.queryParamOrNull("date")
        return ServerResponse
            .status(HttpStatus.OK)
            .json()
            .bodyValueAndAwait(queryArticleListService.execute(page, date))
    }
}