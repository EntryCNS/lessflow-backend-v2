package com.dgswcns.thirdparty.news.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("news")
data class NewsProperties(
    val key: String
)