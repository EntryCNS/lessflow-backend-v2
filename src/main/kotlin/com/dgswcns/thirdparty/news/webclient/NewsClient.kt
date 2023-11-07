package com.dgswcns.thirdparty.news.webclient

import com.dgswcns.global.error.GlobalExceptions
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

    private val baseUrl = "https://newsdata.io/api/1/news?language=ko&full_content=1&image=1&apikey=${newsProperties.key}"
    suspend fun searchNewsByQ(q: String): NewsResponse {
        val uri: String = UriComponentsBuilder
            .fromHttpUrl(baseUrl)
            .queryParam("size", 1)
            .queryParam("q", q)
            .toUriString()
        return webClient.get()
            .uri(uri)
            .retrieve()
            .onStatus({ status -> status.isError }) {
                throw GlobalExceptions.InternalServerError()
            }
            .awaitBody()
    }

    suspend fun getLatestNews(): NewsResponse {
        val uri: String = UriComponentsBuilder
            .fromHttpUrl(baseUrl)
            .queryParam("size", 5)
            .toUriString()
        return webClient.get()
            .uri(uri)
            .retrieve()
            .onStatus({ status -> status.isError }) {
                throw GlobalExceptions.InternalServerError()
            }
            .awaitBody()
    }
}