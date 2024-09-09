package kz.bcm.b2b.domain.repository.datasource

import kz.bcm.b2b.domain.data.cart.event.PostCart
import kz.bcm.b2b.domain.data.cart.mini.GetCartMini
import kz.bcm.b2b.domain.data.wishlistAndCompare.GetMini

interface ProductActionsDataSource {

    suspend fun eventCompare(prodId: String): List<GetMini>
    suspend fun getMiniCompare(): List<GetMini>

    suspend fun eventFavorite(prodId: String): List<GetMini>
    suspend fun getMiniFavorite(): List<GetMini>


    suspend fun eventCart(item: PostCart): GetCartMini
    suspend fun getMiniCart(): GetCartMini
    suspend fun removeCart(id: Int): GetCartMini
}
