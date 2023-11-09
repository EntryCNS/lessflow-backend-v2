package com.dgswcns.thirdparty.mail.webclient

import com.dgswcns.global.error.GlobalExceptions
import com.dgswcns.thirdparty.mail.properties.NcpMailProperties
import com.dgswcns.thirdparty.mail.webclient.dto.request.MailRequestBody
import org.apache.tomcat.util.codec.binary.Base64
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.reactive.function.client.awaitBody
import org.springframework.web.util.UriComponentsBuilder
import java.io.UnsupportedEncodingException
import java.nio.charset.StandardCharsets
import java.security.InvalidKeyException
import java.security.NoSuchAlgorithmException
import javax.crypto.Mac
import javax.crypto.spec.SecretKeySpec

@Component
class NcpMailClient(
    private val webClient: WebClient,
    private val ncpMailProperties: NcpMailProperties
) {
    suspend fun send(email: String, title: String, message: String) {
        val time = System.currentTimeMillis()
        val signature: String = makeSignature(
            time, ncpMailProperties.accessKey, ncpMailProperties.secretKey
        )
        val uri: String = UriComponentsBuilder
            .fromHttpUrl("https://mail.apigw.ntruss.com/api/v1/mails")
            .toUriString()
        return webClient.post()
            .uri(uri)
            .header("x-ncp-apigw-timestamp", time.toString())
            .header("x-ncp-iam-access-key", ncpMailProperties.accessKey)
            .header("x-ncp-apigw-signature-v2", signature)
            .bodyValue(
                MailRequestBody(
                    ncpMailProperties.sender,
                    title,
                    message,
                    listOf(
                        MailRequestBody.MailRequestRecipient(
                            email,
                            "R"
                        )
                    )
                )
            )
            .retrieve()
            .onStatus({ status -> status.isError }) {
                throw GlobalExceptions.InternalServerError()
            }
            .awaitBody()
    }

    @Throws(NoSuchAlgorithmException::class, UnsupportedEncodingException::class, InvalidKeyException::class)
    private fun makeSignature(time: Long, access: String, secret: String): String {
        val space = " "

        val newLine = "\n"

        val method = "POST"

        val url = "/api/v1/mails"

        val message: String = StringBuilder()
            .append(method)
            .append(space)
            .append(url)
            .append(newLine)
            .append(time)
            .append(newLine)
            .append(ncpMailProperties.accessKey)
            .toString()

        val signingKey = SecretKeySpec(secret.toByteArray(StandardCharsets.UTF_8), "HmacSHA256")
        val mac = Mac.getInstance("HmacSHA256")
        mac.init(signingKey)

        val rawHmac = mac.doFinal(message.toByteArray(StandardCharsets.UTF_8))

        return Base64.encodeBase64String(rawHmac)

    }
}