package kz.bcm.b2b.domain.repository.datasource

import kz.bcm.b2b.domain.data.auth.AccessToken
import kz.bcm.b2b.domain.data.auth.login.PostLogin
import kz.bcm.b2b.domain.data.auth.register.PostRegistration

interface AuthDataSource {

    suspend fun login(body: PostLogin): AccessToken
    suspend fun registration(body: PostRegistration): AccessToken
}