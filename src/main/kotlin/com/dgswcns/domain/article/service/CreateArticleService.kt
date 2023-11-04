package com.dgswcns.domain.article.service

import com.dgswcns.domain.article.handler.dto.request.CreateArticleRequest
import com.dgswcns.domain.article.persistence.dao.ArticleRepository
import com.dgswcns.domain.article.persistence.entity.Article
import com.dgswcns.domain.article.persistence.entity.value.Body
import com.dgswcns.domain.article.persistence.entity.value.Thumbnail
import com.dgswcns.domain.article.persistence.entity.value.Video.Companion.invoke
import com.dgswcns.global.util.NlpUtil
import com.dgswcns.thirdparty.news.SearchNewsService
import com.dgswcns.thirdparty.shotstack.RenderVideoService
import org.springframework.stereotype.Service

@Service
class CreateArticleService(
    private val searchNewsService: SearchNewsService,
    private val renderVideoService: RenderVideoService,
    private val nlpUtil: NlpUtil,
    private val articleRepository: ArticleRepository
) {
    suspend fun create(request: CreateArticleRequest) {
        val news = searchNewsService.searchNewsByKeyword(request.keyword)
        val shortContents = shortContent(news.article.content)
        val video = renderVideoService.execute(
            imageUrl = news.article.thumbnail,
            title = news.article.title,
            keyword = news.keyword,
            content =  shortContents
        )
        articleRepository.save(
            Article(
                keyword = request.keyword,
                body = Body(
                    news.article.title,
                    shortContents.joinToString("/n"),
                    Thumbnail(news.article.thumbnail)
                ),
                video = invoke(video.response.id)
            )
        )
    }

    private suspend fun shortContent(content: String): List<String> {
        val sentences = nlpUtil.getSentencesFromText(content)

        val filter1 = nlpUtil.filter1(sentences)
        return nlpUtil.filter2(filter1)
    }
}