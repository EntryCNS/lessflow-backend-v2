package com.dgswcns.domain.article.persistence.dao

import com.dgswcns.domain.article.persistence.entity.Article
import kotlinx.coroutines.flow.Flow
import org.springframework.data.mongodb.repository.ReactiveMongoRepository
import org.springframework.data.repository.kotlin.CoroutineCrudRepository
import org.springframework.stereotype.Repository
import java.awt.print.Pageable

@Repository
interface ArticleRepository : CoroutineCrudRepository<Article, String>{
    fun findAllByCreateAt(date: String): Flow<Article>

}