package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.order.orders.OrderDetails
import kz.bcm.b2b.domain.data.order.orders.PostOrders
import kz.bcm.b2b.domain.repository.Repository

class PostOrdersUseCase(
    private val repository: Repository
) {

    suspend fun execute(postOrder: PostOrders): OrderDetails {
        return repository.postOrders(postOrder)
    }
}