package kz.bcm.b2b.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import kz.bcm.b2b.data.dto.cart.full.GetCartFullDto
import kz.bcm.b2b.data.dto.cart.mini.GetCartMiniDto
import kz.bcm.b2b.data.dto.compare.CompareFullDto
import kz.bcm.b2b.data.dto.wishlistAndCompare.GetMiniDto
import kz.bcm.b2b.data.dto.wishlistAndCompare.wishlist.WishListFullDto
import kz.bcm.b2b.domain.data.cart.event.PostCart
import kz.bcm.b2b.domain.data.cart.full.GetCartFull
import kz.bcm.b2b.domain.data.cart.mini.GetCartMini
import kz.bcm.b2b.domain.data.compare.CompareFull
import kz.bcm.b2b.domain.data.wishlistAndCompare.GetMini
import kz.bcm.b2b.domain.data.wishlistAndCompare.wishlist.WishListFull
import kz.bcm.b2b.domain.repository.datasource.ProductActionsDataSource

class ProductActionsDataSourceImpl(
    private val httpClient: HttpClient
): ProductActionsDataSource {

    override suspend fun eventCompare(prodId: String): List<GetMini> {
        val response: List<GetMiniDto> = httpClient.post("compare/$prodId").body()

        return response
    }

    override suspend fun getMiniCompare(): List<GetMini> {
        val response: List<GetMiniDto> = httpClient.get("compare/mini").body()

        return response
    }

    override suspend fun getFullCompare(): CompareFull {
        val response: CompareFullDto = httpClient.get("compare").body()

        return response
    }



    override suspend fun eventFavorite(prodId: String): List<GetMini> {
        val response: List<GetMiniDto> = httpClient.post("wishlist/$prodId").body()

        return response
    }

    override suspend fun getMiniFavorite(): List<GetMini> {
        val response: List<GetMiniDto> = httpClient.get("wishlist/mini").body()

        return response
    }

    override suspend fun getFullFavorite(): List<WishListFull> {
        val response: List<WishListFullDto> = httpClient.get("wishlist").body()

        return response
    }



    override suspend fun eventCart(item: PostCart): GetCartMini {
        val response: GetCartMiniDto = httpClient.post("cart") {
            setBody(item)
        }.body()

        return response
    }

    override suspend fun getMiniCart(): GetCartMini {
        val response: GetCartMiniDto = httpClient.get("cart/mini").body()

        return response
    }

    override suspend fun getFullCart(): GetCartFull {
        val response: GetCartFullDto = httpClient.get("cart").body()

        return response
    }


    override suspend fun removeCart(id: Int): GetCartMini {
        val response: GetCartMiniDto = httpClient.delete("cart/$id").body()

        return response
    }


}