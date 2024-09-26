package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.auth.passwordCodeSend.PasswordSend
import kz.bcm.b2b.domain.repository.Repository

class PostPasswordCodeSendUseCase(
    private val repository: Repository
) {

    suspend fun execute(passwordSend: PasswordSend) {
        repository.passwordCodeSend(passwordSend)
    }
}