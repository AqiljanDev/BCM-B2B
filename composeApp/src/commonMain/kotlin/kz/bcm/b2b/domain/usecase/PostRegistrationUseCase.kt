package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.auth.AccessToken
import kz.bcm.b2b.domain.data.auth.register.PostRegistration
import kz.bcm.b2b.domain.repository.Repository

class PostRegistrationUseCase(
    private val repository: Repository
) {

    suspend fun execute(body: PostRegistration): AccessToken {
        return repository.registration(body)
    }
}