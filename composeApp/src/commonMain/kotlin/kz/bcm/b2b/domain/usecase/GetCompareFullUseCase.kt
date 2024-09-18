package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.compare.CompareFull
import kz.bcm.b2b.domain.repository.Repository

class GetCompareFullUseCase(
    private val repository: Repository
) {

    suspend fun execute(): CompareFull {
        return repository.getFullCompare()
    }
}