package com.dgswcns.thirdparty.shotstack.webclient

import com.dgswcns.global.error.GlobalExceptions
import com.dgswcns.thirdparty.shotstack.properties.ShotstackProperties
import com.dgswcns.thirdparty.shotstack.webclient.dto.request.RenderVideoRequest
import com.dgswcns.thirdparty.shotstack.webclient.dto.response.RenderVideoResponse
import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.ObjectMapper
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.UriComponentsBuilder

@Component
class ShotstackClient(
    private val webClient: WebClient,
    private val shotstackProperties: ShotstackProperties
) {
    suspend fun renderVideo(imageUrl: String,
                            title: String,
                            keyword: String,
                            content: List<String>
    ): RenderVideoResponse {
        val uri: String = UriComponentsBuilder
            .fromHttpUrl("https://api.shotstack.io/edit/stage/render")
            .build()
            .toUriString()
        val mapper = ObjectMapper().setSerializationInclusion(JsonInclude.Include.NON_NULL)
        val captions = mutableListOf<RenderVideoRequest.Timeline.Track.Clip>()

        //TODO refactoring
        val length = 3.0 * content.size
        var start = 0.0
        for (caption in content) {
            captions.add(
                RenderVideoRequest.Timeline.Track.Clip(
                    asset = RenderVideoRequest.Timeline.Track.Clip.Asset(
                        type = "html",
                        width = 880,
                        height = 636,
                        html = "<p data-html-type=\\\"text\\\">${caption}</p>",
                        css = "p { color: #ffffff; font-size: 44px; font-family: \\\"Montserrat ExtraBold\\\"; text-align: center; }",
                    ),
                    start = start,
                    fit = "none",
                    scale = 1.0,
                    offset = RenderVideoRequest.Timeline.Track.Clip.Offset(
                        x = 0.003,
                        y = -0.308
                    ),
                    position = "center",
                    length = 3.0
                )
            )
            start += 3.0
        }

        val request = mapper.writeValueAsString(
            RenderVideoRequest(
                timeline = RenderVideoRequest.Timeline(
                    fonts = listOf(
                        RenderVideoRequest.Timeline.Font(
                            "https://shotstack-ingest-api-v1-sources.s3.ap-southeast-2.amazonaws.com/o00a4l39cg/zzy91daj-2qhw-9k4l-ayc5-4yixzx4yjkkf/source.otf"
                        )
                    ),
                    background = "#000000",
                    tracks = listOf(
                        RenderVideoRequest.Timeline.Track(
                            captions +
                                    listOf(
                                        RenderVideoRequest.Timeline.Track.Clip(
                                            asset = RenderVideoRequest.Timeline.Track.Clip.Asset(
                                                type = "html",
                                                width = 525,
                                                height = 73,
                                                html = "<p data-html-type=\\\"text\\\">#${keyword}</p>",
                                                css = "p { color: #ffffff; font-size: 50px; font-family: \\\"Montserrat ExtraBold\\\"; text-align: center; }"
                                            ),
                                            start = 0.0,
                                            length = length,
                                            fit = "none",
                                            scale = 1.0,
                                            offset = RenderVideoRequest.Timeline.Track.Clip.Offset(
                                                x = -0.014,
                                                y = 0.435
                                            ),
                                            position = "center"
                                        ),
                                        RenderVideoRequest.Timeline.Track.Clip(
                                            asset = RenderVideoRequest.Timeline.Track.Clip.Asset(
                                                type = "image",
                                                src = imageUrl
                                            ),
                                            start = 0.0,
                                            length = length,
                                            offset = RenderVideoRequest.Timeline.Track.Clip.Offset(
                                                x = -0.001,
                                                y = 0.053
                                            ),
                                            position = "center",
                                            scale = 0.354
                                        ),
                                        RenderVideoRequest.Timeline.Track.Clip(
                                            asset = RenderVideoRequest.Timeline.Track.Clip.Asset(
                                                type = "html",
                                                width = 776,
                                                height = 234,
                                                html = "<p data-html-type=\"text\">${title}</p>",
                                                css = "p { color: #ffffff; font-size: 64px; font-family: \"Montserrat ExtraBold\"; text-align: center; }"
                                            ),
                                            start = 0.0,
                                            length = length,
                                            fit = "none",
                                            scale = 1.0,
                                            offset = RenderVideoRequest.Timeline.Track.Clip.Offset(
                                                x = -0.005,
                                                y = 0.303
                                            ),
                                            position = "center"
                                        )
                                    )
                        )
                    )
                ),
                output = RenderVideoRequest.Output(
                    format = "mp4",
                    fps = 25,
                    size = RenderVideoRequest.Output.Size(
                        width = 1040,
                        height = 1980
                    ),
                    destinations = listOf(
                        RenderVideoRequest.Output.Destinations(
                            provider = "s3",
                            options = RenderVideoRequest.Output.Destinations.Options(
                                region = shotstackProperties.s3.region,
                                bucket = shotstackProperties.s3.bucket
                            )
                        )
                    )
                )
            )
        )

        return webClient.post()
            .uri(uri)
            .header("x-api-key", shotstackProperties.key)
            .bodyValue(request)
            .retrieve()
            .onStatus({ status -> status.isError }) {
                throw GlobalExceptions.InternalServerError()
            }
            .awaitBody()
    }

}