package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.order.findMyOrder.MyOrder
import kz.bcm.b2b.domain.repository.Repository

class GetMyOrdersUseCase(
    private val repository: Repository
) {

    suspend fun execute(): List<MyOrder> {
        return repository.getMyOrder()
    }
}