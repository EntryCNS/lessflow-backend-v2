package com.dgswcns.thirdparty.mail.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("ncp.mail")
data class NcpMailProperties(
    val accessKey: String,
    val secretKey: String,
    val sender: String
)