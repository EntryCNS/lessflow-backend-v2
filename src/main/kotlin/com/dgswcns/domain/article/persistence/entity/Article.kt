package com.dgswcns.domain.article.persistence.entity

import com.dgswcns.domain.article.persistence.entity.value.Body
import com.dgswcns.domain.article.persistence.entity.value.Video
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.index.Indexed
import org.springframework.data.mongodb.core.mapping.Document
import java.util.*

@Document
data class Article (
    @Id
    val id: String? = null,
    @Indexed(name = "keyword")
    val keyword: String,
    @Indexed(name = "body")
    val body: Body,
    @Indexed(name = "video")
    val video: Video,
    @CreatedDate
    @Indexed(name = "createAt")
    val createAt: Date? = null
)