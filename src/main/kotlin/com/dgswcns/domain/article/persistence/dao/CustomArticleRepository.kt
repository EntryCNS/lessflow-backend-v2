package com.dgswcns.domain.article.persistence.dao

import com.dgswcns.domain.article.persistence.entity.Article
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.data.domain.Pageable
import org.springframework.data.mongodb.core.ReactiveMongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.data.support.PageableExecutionUtils
import org.springframework.stereotype.Component
import java.util.function.LongSupplier

@Component
class CustomArticleRepository(
    private val reactiveMongoTemplate: ReactiveMongoTemplate
) {
    fun findAllByDateWithPaging(
        page: String,
        date: String?
    ): Page<Article>  {

        val pageable: Pageable =
            PageRequest.of(Integer.parseInt(page) -1, 5)

        val query = Query()

        if (date != null) {
            query.addCriteria(Criteria.where("createAt").`is`(date))
        }

        query
            .with(pageable)
            .skip((pageable.pageSize * pageable.pageNumber).toLong())
            .limit(pageable.pageSize)

        val filteredMetaData: MutableList<Article>? =
            reactiveMongoTemplate.find(
                query,
                Article::class.java,
                "article"
            ).collectList().toFuture().get()

        //val a = filteredMetaData.collectList().toFuture().get()
        return PageableExecutionUtils.getPage(
            filteredMetaData!!,
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