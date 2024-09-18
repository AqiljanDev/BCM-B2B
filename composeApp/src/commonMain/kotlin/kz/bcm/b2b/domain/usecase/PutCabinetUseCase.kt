package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.cabinet.Cabinet
import kz.bcm.b2b.domain.repository.Repository

class PutCabinetUseCase(
    private val repository: Repository
) {

    suspend fun execute(cabinet: Cabinet): Cabinet {
        return repository.updateCabinet(cabinet)
    }
}