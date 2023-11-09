package com.dgswcns.thirdparty.mail.webclient.dto.request

data class MailRequestBody(
    val senderAddress: String,
    val title: String,
    val body: String,
    val recipients: List<MailRequestRecipient>
) {
    data class MailRequestRecipient(
        val address: String,
        val type: String
    )
}