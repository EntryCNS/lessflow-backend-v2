package com.dgswcns.domain.article.handler

import com.dgswcns.domain.article.handler.dto.request.CreateArticleRequest
import com.dgswcns.domain.article.service.CreateArticleService
import com.dgswcns.domain.article.service.QueryArticleListService
import com.dgswcns.domain.article.service.QueryArticleDetailService
import com.dgswcns.domain.article.service.QueryArticleVideoService
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
        createArticleService.create(request.extractBody<CreateArticleRequest>().awaitSingle())
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
                queryArticleVideoService.execute(request.pathVariable("id"))
            )
    }

    suspend fun getArticles(request: ServerRequest): ServerResponse {
        return ServerResponse.status(HttpStatus.OK).bodyValueAndAwait(queryArticleListService.execute())
    }
}