package com.dgswcns.domain.article.persistence.entity.value

data class Video
private constructor(val url: String) {
    companion object {
        operator fun invoke(videoId: String): Video {
            return Video("https://lessflow.s3.ap-northeast-2.amazonaws.com/${videoId}.mp4")
        }
    }
}