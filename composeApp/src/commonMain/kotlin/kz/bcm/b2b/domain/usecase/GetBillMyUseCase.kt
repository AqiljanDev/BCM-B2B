package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.bill.BillMy
import kz.bcm.b2b.domain.repository.Repository

class GetBillMyUseCase(
    private val repository: Repository
) {

    suspend fun execute(): List<BillMy> {
        return repository.getBillMy()
    }
}