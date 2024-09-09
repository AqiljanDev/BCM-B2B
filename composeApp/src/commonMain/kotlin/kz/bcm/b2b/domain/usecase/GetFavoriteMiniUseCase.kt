package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.wishlistAndCompare.GetMini
import kz.bcm.b2b.domain.repository.Repository

class GetFavoriteMiniUseCase(
    private val repository: Repository
) {

    suspend fun execute(): List<GetMini> {
        return repository.getMiniFavorite()
    }
}