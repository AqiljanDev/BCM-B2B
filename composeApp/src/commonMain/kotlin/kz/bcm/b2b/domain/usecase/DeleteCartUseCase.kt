package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.cart.mini.GetCartMini
import kz.bcm.b2b.domain.repository.Repository

class DeleteCartUseCase(
    private val repository: Repository
) {

    suspend fun execute(id: Int): GetCartMini {
        return repository.deleteCart(id)
    }
}
