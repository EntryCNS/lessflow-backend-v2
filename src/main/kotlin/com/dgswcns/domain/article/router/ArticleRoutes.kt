package com.dgswcns.domain.article.router

import com.dgswcns.domain.article.handler.ArticleHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.reactive.function.server.coRouter

@Configuration
class ArticleRoutes {
    @Bean
    fun articleRouter(articleHandler: ArticleHandler) = coRouter {
        "/article".nest {
            POST("/create") {articleHandler.create(it)}
            GET("/list") {articleHandler.getArticles(it)}
            GET("/{id}") {articleHandler.getArticleById(it)}
            GET("/video/{id}") {articleHandler.getArticleVideoById(it)}
        }
    }
}