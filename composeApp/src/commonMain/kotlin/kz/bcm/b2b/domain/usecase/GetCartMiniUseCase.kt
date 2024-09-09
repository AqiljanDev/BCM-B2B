package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.cart.mini.GetCartMini
import kz.bcm.b2b.domain.repository.Repository

class GetCartMiniUseCase(
    private val repository: Repository
) {

    suspend fun execute(): GetCartMini {
        return repository.getMiniCart()
    }
}