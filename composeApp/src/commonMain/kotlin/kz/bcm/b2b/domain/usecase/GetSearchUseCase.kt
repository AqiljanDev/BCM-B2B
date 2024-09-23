package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.findOneCatalog.Product
import kz.bcm.b2b.domain.repository.Repository

class GetSearchUseCase(
    private val repository: Repository
) {

    suspend fun execute(value: String): List<Product> {
        return repository.search(value)
    }
}