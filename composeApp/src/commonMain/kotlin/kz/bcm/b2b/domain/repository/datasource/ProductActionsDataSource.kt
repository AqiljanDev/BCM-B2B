package kz.bcm.b2b.domain.repository.datasource

import kz.bcm.b2b.domain.data.cart.event.PostCart
import kz.bcm.b2b.domain.data.cart.full.GetCartFull
import kz.bcm.b2b.domain.data.cart.mini.GetCartMini
import kz.bcm.b2b.domain.data.compare.CompareFull
import kz.bcm.b2b.domain.data.sale.GetSaleAll
import kz.bcm.b2b.domain.data.sale.GetSaleOne
import kz.bcm.b2b.domain.data.wishlistAndCompare.GetMini
import kz.bcm.b2b.domain.data.wishlistAndCompare.wishlist.WishListFull

interface ProductActionsDataSource {

    suspend fun eventCompare(prodId: String): List<GetMini>
    suspend fun getMiniCompare(): List<GetMini>
    suspend fun getFullCompare(): CompareFull

    suspend fun eventFavorite(prodId: String): List<GetMini>
    suspend fun getMiniFavorite(): List<GetMini>
    suspend fun getFullFavorite(): List<WishListFull>


    suspend fun eventCart(item: PostCart): GetCartMini
    suspend fun getMiniCart(): GetCartMini
    suspend fun getFullCart(): GetCartFull
    suspend fun removeCart(id: Int): GetCartMini


    suspend fun findAllSale(): List<GetSaleAll>
    suspend fun findOneSale(slug: String): GetSaleOne
}
