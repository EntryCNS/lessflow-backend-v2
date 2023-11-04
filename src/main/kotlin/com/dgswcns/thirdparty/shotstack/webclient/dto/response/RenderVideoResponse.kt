package com.dgswcns.thirdparty.shotstack.webclient.dto.response

data class RenderVideoResponse(
    val success: Boolean,
    val message: String,
    val response: Response
) {
    data class Response(
        val message: String,
        val id: String
    )
}