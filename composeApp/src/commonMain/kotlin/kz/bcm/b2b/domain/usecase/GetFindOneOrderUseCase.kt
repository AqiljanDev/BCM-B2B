package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.order.findOne.FindOneProduct
import kz.bcm.b2b.domain.repository.Repository

class GetFindOneOrderUseCase(
    private val repository: Repository
) {

    suspend fun execute(id: Int): FindOneProduct {
        return repository.getFindOneOrder(id)
    }
}