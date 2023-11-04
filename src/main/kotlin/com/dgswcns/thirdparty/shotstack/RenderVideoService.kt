package com.dgswcns.thirdparty.shotstack

import com.dgswcns.thirdparty.shotstack.webclient.ShotstackClient
import com.dgswcns.thirdparty.shotstack.webclient.dto.response.RenderVideoResponse
import org.springframework.stereotype.Service

@Service
class RenderVideoService(
    private val shotstackClient: ShotstackClient
) {
    suspend fun execute(
        imageUrl: String,
        title: String,
        keyword: String,
        content: List<String>
    ): RenderVideoResponse {
        return shotstackClient.renderVideo(
            imageUrl,
            title,
            keyword,
            content
        )
    }
}