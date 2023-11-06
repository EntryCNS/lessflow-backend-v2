package com.dgswcns.global.extensions

import org.springframework.web.reactive.function.server.ServerRequest

internal inline fun <reified T : Any> ServerRequest.extractBody() = this.bodyToMono(T::class.java)

internal fun ServerRequest.extractPath(path: String) = this.pathVariable(path)