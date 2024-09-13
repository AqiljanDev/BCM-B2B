package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.cart.full.GetCartFull
import kz.bcm.b2b.domain.repository.Repository

class GetCartFullUseCase(
    private val repository: Repository
) {

    suspend fun execute(): GetCartFull {
        return repository.getFullCart()
    }
}