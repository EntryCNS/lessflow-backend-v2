package com.dgswcns.thirdparty.news.exception

import com.dgswcns.global.error.LessflowException
import org.springframework.http.HttpStatus

sealed class NewsExceptions(
    override val status: HttpStatus,
    override val message: String
) : LessflowException(status, message){
    class NewsNotFoundException(message: String = NEWS_NOT_FOUND) : NewsExceptions(HttpStatus.NOT_FOUND, NEWS_NOT_FOUND)

    companion object {
        private const val NEWS_NOT_FOUND = "뉴스를 찾지 못하였습니다"
    }
}