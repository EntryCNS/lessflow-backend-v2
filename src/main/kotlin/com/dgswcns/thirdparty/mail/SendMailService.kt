package com.dgswcns.thirdparty.mail

import com.dgswcns.thirdparty.mail.webclient.NcpMailClient
import org.springframework.stereotype.Service

@Service
class SendMailService(
    private val ncpMailClient: NcpMailClient
) {
    suspend fun send(email: String, title: String, message: String) {
        ncpMailClient.send(email, title, message)
    }

}