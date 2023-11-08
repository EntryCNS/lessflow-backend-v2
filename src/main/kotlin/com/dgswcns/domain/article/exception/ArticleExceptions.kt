package com.dgswcns.domain.article.exception

import com.dgswcns.global.error.LessflowException
import org.springframework.http.HttpStatus

sealed class ArticleExceptions(
    override val status: HttpStatus,
    override val message: String
) : LessflowException(status, message) {
    class NotFoundArticle(message: String = NOT_FOUND_ARTICLE) : ArticleExceptions(HttpStatus.NOT_FOUND, message)

    companion object {
        private const val NOT_FOUND_ARTICLE = "아티클를 찾지 못하였습니다"
    }
}