package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.wishlistAndCompare.GetMini
import kz.bcm.b2b.domain.repository.Repository

class EventFavoriteUseCase(
    private val repository: Repository
) {

    suspend fun execute(prodId: String): List<GetMini> {
        return repository.eventFavorite(prodId)
    }
}
