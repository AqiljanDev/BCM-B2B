package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.sale.GetSaleAll
import kz.bcm.b2b.domain.repository.Repository

class GetSaleUseCase(
    private val repository: Repository
) {


    suspend fun execute(): List<GetSaleAll> {
        return repository.findAllSale()
    }
}