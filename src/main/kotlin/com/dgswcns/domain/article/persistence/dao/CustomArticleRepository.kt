package com.dgswcns.domain.article.persistence.dao

import com.dgswcns.domain.article.persistence.entity.Article
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Component
import reactor.core.publisher.Flux
import java.util.function.LongSupplier

@Component
class CustomArticleRepository(
    private val reactiveMongoTemplate: ReactiveMongoTemplate
) {
    fun findAllWithPaging(
        page: String
    ): Page<Article>  {

        val pageable: Pageable =
            PageRequest.of(Integer.parseInt(page) -1, 5)

        val query =
            Query()
                .with(pageable)
                .skip((pageable.pageSize * pageable.pageNumber).toLong())
                .limit(pageable.pageSize)

        val filteredMetaData: Flux<Article> =
            reactiveMongoTemplate.find(
                query,
                Article::class.java,
                "article"
            )

        val a = filteredMetaData.collectList().toFuture().get()
        return PageableExecutionUtils.getPage(
            a!!,
            pageable,
            LongSupplier {
                reactiveMongoTemplate.count(
                    query.skip(-1).limit(-1),
                    Article::class.java
                ).toFuture().get()
            }
        )
    }
}