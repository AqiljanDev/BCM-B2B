package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.sale.GetSaleOne
import kz.bcm.b2b.domain.repository.Repository

class GetSaleOneUseCase(
    private val repository: Repository
) {


    suspend fun execute(slug: String): GetSaleOne {
        return repository.findOneSale(slug)
    }
}