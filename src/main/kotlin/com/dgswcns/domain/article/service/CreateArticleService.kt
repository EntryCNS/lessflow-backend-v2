package com.dgswcns.domain.article.service

import com.dgswcns.domain.article.handler.dto.request.CreateArticleRequest
import com.dgswcns.domain.article.persistence.dao.ArticleRepository
import com.dgswcns.domain.article.persistence.entity.Article
import com.dgswcns.domain.article.persistence.entity.value.Body
import com.dgswcns.domain.article.persistence.entity.value.Thumbnail
import com.dgswcns.domain.article.persistence.entity.value.Video.Companion.invoke
import com.dgswcns.global.util.NlpUtil
import com.dgswcns.thirdparty.mail.SendMailService
import com.dgswcns.thirdparty.news.GetLatestNewsService
import com.dgswcns.thirdparty.news.SearchNewsService
import com.dgswcns.thirdparty.shotstack.RenderVideoService
import com.dgswcns.thirdparty.shotstack.webclient.dto.response.RenderVideoResponse
import org.springframework.stereotype.Service

@Service
class CreateArticleService(
    private val searchNewsService: SearchNewsService,
    private val getLatestNewsService: GetLatestNewsService,
    private val renderVideoService: RenderVideoService,
    private val nlpUtil: NlpUtil,
    private val articleRepository: ArticleRepository,
    private val sendMailService: SendMailService
) {

    suspend fun createByKeyword(request: CreateArticleRequest) {
        val news = searchNewsService.searchNewsByKeyword(request.keyword)
        val shortContents = shortContent(news.article.content)
        val video = invoke(renderVideo(news.article.thumbnail, news.article.title, news.keyword, shortContents).response.id)
        articleRepository.save(
            Article(
                keyword = news.keyword,
                body = Body(
                    news.article.title,
                    shortContents.joinToString("\n"),
                    Thumbnail(news.article.thumbnail)
                ),
                video = video
            )
        )
        sendMailService.send(
            request.email,
            "lessf!ow ${request.keyword} 키워드의 뉴스가 도착했어요!",
            StringBuffer("<div style=\"text-align: center; width: 400px\">")
                .append(" <p style=\"font-size: 2rem; font-weight: 700\">\n")
                .append(news.article.title)
                .append("</p>")
                .append("<img src=\"${news.article.thumbnail}\"  style=\"height: 400px; width: 400px\" />\n")
                .append("<p style=\"line-height: 30px; letter-spacing: 0.075rem; font-size: 1rem\">")
                .append(shortContents.joinToString("\n"))
                .append("</p>")
                .append("영상 으로 보러 가기 -> $video")
                .toString()
        )

    }


    private suspend fun renderVideo(imageUrl: String, title: String, keyword: String, content: List<String>): RenderVideoResponse {
        return renderVideoService.execute(imageUrl, title, keyword, content)
    }

    private suspend fun shortContent(content: String): List<String> {
        val sentences = nlpUtil.getSentencesFromText(content)

        val filter1 = nlpUtil.filter1(sentences)
        return nlpUtil.filter2(filter1)
    }
}