package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.auth.AccessToken
import kz.bcm.b2b.domain.data.auth.login.PostLogin
import kz.bcm.b2b.domain.repository.Repository

class PostLoginUseCase(
    private val repository: Repository
) {

    suspend fun execute(body: PostLogin): AccessToken {
        return repository.login(body)
    }
}