package com.dgswcns.thirdparty.shotstack.properties

import org.springframework.boot.context.properties.ConfigurationProperties

@ConfigurationProperties("shotstack")
data class ShotstackProperties(
    val key: String,
    val s3: S3
) {
    data class S3(
        val region: String,
        val bucket: String
    )
}