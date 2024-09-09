package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.cart.event.PostCart
import kz.bcm.b2b.domain.data.cart.mini.GetCartMini
import kz.bcm.b2b.domain.repository.Repository

class EventCartUseCase(
    private val repository: Repository
) {

    suspend fun execute(item: PostCart): GetCartMini {
        return repository.eventCart(item)
    }
}
