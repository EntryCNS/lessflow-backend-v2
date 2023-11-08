package com.dgswcns.domain.article.handler.dto.response

import java.util.*

data class ArticleSimpleResponse(
    val id: String?,
    val keyword: String,
    val thumbnail: String?,
    val createAt: String
)