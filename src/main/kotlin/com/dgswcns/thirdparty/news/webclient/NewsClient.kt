package com.dgswcns.thirdparty.news.webclient

import com.dgswcns.thirdparty.news.properties.NewsProperties
import com.dgswcns.thirdparty.news.webclient.dto.response.NewsResponse
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.UriComponentsBuilder

@Component
class NewsClient(
    private val webClient: WebClient,
    private val newsProperties: NewsProperties
) {
    suspend fun searchNewsByQ(q: String): NewsResponse {
        val uri: String = UriComponentsBuilder
            .fromHttpUrl("https://newsdata.io/api/1/news?language=ko&full_content=1&size=1&image=1")
            .queryParam("apikey", newsProperties.key)
            .queryParam("q", q)
            .toUriString()
        return webClient.get()
            .uri(uri)
            .retrieve()
            .onStatus({ status -> status.isError }) {
                throw RuntimeException()
            }
            .awaitBody()
    }
}