package com.dgswcns.thirdparty.shotstack.webclient.dto.request

data class RenderVideoRequest(
    val timeline: Timeline,
    val output: Output
) {
    data class Timeline (
        val fonts: List<Font>,
        val background: String,
        val tracks: List<Track>
    ) {
        data class Font(
            val src: String
        )
        data class Track(
            val clips: List<Clip>
        ) {
            data class Clip(
                val asset: Asset? = null,
                val fit: String? = null,
                val length: Double? = null,
                val offset: Offset? = null,
                val position: String? = null,
                val scale: Double? = null,
                val start: Double? = null,
                val transition: Transition? = null
            ) {
                data class Asset(
                    val css: String? = null,
                    val height: Int? = null,
                    val html: String? = null,
                    val src: String? = null,
                    val type: String? = null,
                    val volume: Int? = null,
                    val width: Int? = null,
                    val backGround: String? = null
                )

                data class Offset(
                    val x: Double,
                    val y: Double
                )

                data class Transition(
                    val `in`: String,
                    val out: String
                )
            }
        }
    }

    data class Output(
        val destinations: List<Destinations>,
        val format: String,
        val fps: Int,
        val size: Size
    ) {
        data class Destinations(
            val provider: String,
            val options: Options
        ) {
            data class Options(
                val region: String,
                val bucket: String
            )
        }
        data class Size(
            val height: Int,
            val width: Int
        )
    }
}