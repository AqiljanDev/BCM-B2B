package kz.bcm.b2b.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kz.bcm.b2b.data.dto.auth.AccessTokenDto
import kz.bcm.b2b.domain.data.auth.AccessToken
import kz.bcm.b2b.domain.data.auth.login.PostLogin
import kz.bcm.b2b.domain.data.auth.register.PostRegistration
import kz.bcm.b2b.domain.repository.datasource.AuthDataSource

class AuthDataSourceImpl(
    private val httpClient: HttpClient
) : AuthDataSource {

    override suspend fun login(body: PostLogin): AccessToken {
        val response: AccessTokenDto = httpClient.post("auth/login") {
            setBody(body)
        }.body()

        return response
    }

    override suspend fun registration(body: PostRegistration): AccessToken {
        val response: AccessTokenDto = httpClient.post("auth/registration") {
            setBody(body)
        }.body()

        return response
    }


}