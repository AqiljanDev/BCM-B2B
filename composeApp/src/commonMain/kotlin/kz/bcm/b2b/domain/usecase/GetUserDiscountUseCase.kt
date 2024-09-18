package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.findOneCatalog.UserDiscount
import kz.bcm.b2b.domain.repository.Repository

class GetUserDiscountUseCase(
    private val repository: Repository
) {

    suspend fun execute(): List<UserDiscount> {
        return repository.getUserDiscount()
    }
}