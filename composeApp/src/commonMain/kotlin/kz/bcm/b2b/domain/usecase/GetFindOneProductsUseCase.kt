package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.findOneProduct.FindOneProduct
import kz.bcm.b2b.domain.repository.Repository

class GetFindOneProductsUseCase(
    private val repository: Repository
) {

    suspend fun execute(slug: String): FindOneProduct {
        return repository.findOneProduct(slug)
    }
}
