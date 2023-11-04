package com.dgswcns.domain.article.persistence.dao

import com.dgswcns.domain.article.persistence.entity.Article
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository

@Repository
interface ArticleRepository : CoroutineCrudRepository<Article, String>{
}