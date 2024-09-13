package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.wishlistAndCompare.wishlist.WishListFull
import kz.bcm.b2b.domain.repository.Repository

class GetFavoriteFullUseCase(
    private val repository: Repository
) {

    suspend fun execute(): List<WishListFull> {
        return repository.getFullFavorite()
    }

}