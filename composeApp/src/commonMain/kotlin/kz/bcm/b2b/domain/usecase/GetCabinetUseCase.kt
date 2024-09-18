package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.cabinet.Cabinet
import kz.bcm.b2b.domain.repository.Repository

class GetCabinetUseCase(
    private val repository: Repository
) {

    suspend fun execute(): Cabinet {
        return repository.getCabinet()
    }
}