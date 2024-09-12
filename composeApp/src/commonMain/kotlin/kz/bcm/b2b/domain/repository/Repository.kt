package kz.bcm.b2b.domain.repository

import kz.bcm.b2b.domain.data.cart.event.PostCart
import kz.bcm.b2b.domain.data.cart.mini.GetCartMini
import kz.bcm.b2b.domain.data.collectCharacters.CollectCharacters
import kz.bcm.b2b.domain.data.findOneCatalog.Catalog
import kz.bcm.b2b.domain.data.findOneProduct.FindOneProduct
import kz.bcm.b2b.domain.data.wishlistAndCompare.GetMini

interface Repository {

    suspend fun findOne(
        category: String,
        min: Int?,
        max: Int?,
        sort: String,
        f: String,
        page: Int
    ): Catalog

    suspend fun collectCharacters(
        category: String,
        min: Int?,
        f: String
    ): CollectCharacters


    suspend fun findOneProduct(slug: String): FindOneProduct


    suspend fun eventCompare(prodId: String): List<GetMini>
    suspend fun getMiniCompare(): List<GetMini>

    suspend fun eventFavorite(prodId: String): List<GetMini>
    suspend fun getMiniFavorite(): List<GetMini>

    suspend fun eventCart(item: PostCart): GetCartMini
    suspend fun getMiniCart(): GetCartMini
    suspend fun deleteCart(id: Int): GetCartMini
}