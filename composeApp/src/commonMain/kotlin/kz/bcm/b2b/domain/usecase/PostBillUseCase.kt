package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.bill.BillBody
import kz.bcm.b2b.domain.data.bill.BillMy
import kz.bcm.b2b.domain.repository.Repository

class PostBillUseCase(
    private val repository: Repository
) {

    suspend fun execute(body: BillBody): BillMy {
        return repository.createBill(body)
    }
}