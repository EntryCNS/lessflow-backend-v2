package com.dgswcns.global.error

import org.springframework.http.HttpStatus

sealed class GlobalExceptions(
    override val status: HttpStatus,
    override val message: String
) : LessflowException(status, message) {
    class InternalServerError(message: String = INTERNAL_SERVER_ERROR) : GlobalExceptions(HttpStatus.INTERNAL_SERVER_ERROR, message)

    class BadRequestError(message: String = BAD_REQUEST_ERROR) : GlobalExceptions(HttpStatus.BAD_REQUEST, message)
    class RequestHandlerNotMatchesException(message: String = REQUEST_HANDLER_NOT_MATCHES) : GlobalExceptions(HttpStatus.BAD_REQUEST, message)
    companion object {
        private const val INTERNAL_SERVER_ERROR = "내부 서버 오류"
        private const val BAD_REQUEST_ERROR = "잘못된 요청 오류"
        private const val REQUEST_HANDLER_NOT_MATCHES = "찾을 수 없는 요청 주소"
    }
}