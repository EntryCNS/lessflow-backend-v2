package com.dgswcns.global.error

import org.junit.jupiter.api.Order
import org.springframework.boot.autoconfigure.web.WebProperties
import org.springframework.boot.autoconfigure.web.reactive.error.AbstractErrorWebExceptionHandler
import org.springframework.boot.web.reactive.error.ErrorAttributes
import org.springframework.context.ApplicationContext
import org.springframework.http.codec.ServerCodecConfigurer
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.server.*
import org.springframework.web.server.ResponseStatusException
import org.springframework.web.server.ServerWebInputException
import reactor.core.publisher.Mono


@Component
@Order(-2)
class GlobalExceptionHandler(
    errorAttributes: ErrorAttributes,
    webProperties: WebProperties,
    applicationContext: ApplicationContext,
    serverCodecConfigurer: ServerCodecConfigurer
) : AbstractErrorWebExceptionHandler(
    errorAttributes,
    webProperties.resources,
    applicationContext
)  {
    init {
        super.setMessageReaders(serverCodecConfigurer.readers)
        super.setMessageWriters(serverCodecConfigurer.writers)
    }

    override fun getRoutingFunction(errorAttributes: ErrorAttributes?): RouterFunction<ServerResponse> =
        RouterFunctions.route(RequestPredicates.all(), this::handleError)

    private fun handleError(request: ServerRequest): Mono<ServerResponse> =
        when (val throwable = super.getError(request)) {
            is LessflowException -> buildErrorResponse(throwable)
            is ServerWebInputException -> buildErrorResponse(GlobalExceptions.BadRequestError())
            is ResponseStatusException -> buildErrorResponse(GlobalExceptions.RequestHandlerNotMatchesException())
            else -> buildErrorResponse(GlobalExceptions.InternalServerError(throwable.message!!))
        }

    private fun buildErrorResponse(exception: LessflowException) =
        ServerResponse.status(exception.status)
            .bodyValue(
                ErrorResponse(
                    exception.status,
                    exception.message
                )
            )
}