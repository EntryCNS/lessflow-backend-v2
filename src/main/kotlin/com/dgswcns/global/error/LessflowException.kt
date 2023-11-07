package com.dgswcns.global.error

import org.springframework.http.HttpStatus

abstract class LessflowException(
    open val status: HttpStatus,
    override val message: String
): RuntimeException()