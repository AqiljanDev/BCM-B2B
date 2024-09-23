package kz.bcm.b2b.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.delete
import io.ktor.client.request.get
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.HttpMethod
import io.ktor.http.appendPathSegments
import kz.bcm.b2b.data.dto.cart.full.GetCartFullDto
import kz.bcm.b2b.data.dto.cart.mini.GetCartMiniDto
import kz.bcm.b2b.data.dto.compare.CompareFullDto
import kz.bcm.b2b.data.dto.wishlistAndCompare.GetMiniDto
import kz.bcm.b2b.data.dto.wishlistAndCompare.wishlist.WishListFullDto
import kz.bcm.b2b.di.ErrorResponse
import kz.bcm.b2b.domain.data.cart.event.PostCart
import kz.bcm.b2b.domain.data.cart.full.GetCartFull
import kz.bcm.b2b.domain.data.cart.mini.GetCartMini
import kz.bcm.b2b.domain.data.compare.CompareFull
import kz.bcm.b2b.domain.data.wishlistAndCompare.GetMini
import kz.bcm.b2b.domain.data.wishlistAndCompare.wishlist.WishListFull
import kz.bcm.b2b.domain.repository.datasource.ProductActionsDataSource
import kz.bcm.b2b.presentation.other.ApiResponse
import kz.bcm.b2b.presentation.other.safeRequest

class ProductActionsDataSourceImpl(
    private val httpClient: HttpClient
): ProductActionsDataSource {

    override suspend fun eventCompare(prodId: String): List<GetMini> {

        val entry = httpClient.safeRequest<List<GetMiniDto>, ErrorResponse> {
            method = HttpMethod.Post
            url {
                appendPathSegments("compare/$prodId")
            }
        }

        return if (entry is ApiResponse.Success) entry.body else listOf()
    }

    override suspend fun getMiniCompare(): List<GetMini> {

        val entry = httpClient.safeRequest<List<GetMiniDto>, ErrorResponse> {
            method = HttpMethod.Get
            url {
                appendPathSegments("compare/mini")
            }
        }

        return if (entry is ApiResponse.Success) entry.body else listOf()
    }

    override suspend fun getFullCompare(): CompareFull {

        val entry = httpClient.safeRequest<CompareFullDto, ErrorResponse> {
            method = HttpMethod.Get
            url {
                appendPathSegments("compare")
            }
        }

        return if (entry is ApiResponse.Success) entry.body else CompareFullDto()
    }



    override suspend fun eventFavorite(prodId: String): List<GetMini> {

        val entry = httpClient.safeRequest<List<GetMiniDto>, ErrorResponse> {
            method = HttpMethod.Post
            url {
                appendPathSegments("wishlist/$prodId")
            }
        }

        return if (entry is ApiResponse.Success) entry.body else listOf()
    }

    override suspend fun getMiniFavorite(): List<GetMini> {

        val entry = httpClient.safeRequest<List<GetMiniDto>, ErrorResponse> {
            method = HttpMethod.Get
            url {
                appendPathSegments("wishlist/mini")
            }
        }

        return if (entry is ApiResponse.Success) entry.body else listOf()
    }

    override suspend fun getFullFavorite(): List<WishListFull> {

        val entry = httpClient.safeRequest<List<WishListFullDto>, ErrorResponse> {
            method = HttpMethod.Get
            url {
                appendPathSegments("wishlist")
            }
        }

        return if (entry is ApiResponse.Success) entry.body else listOf()
    }



    override suspend fun eventCart(item: PostCart): GetCartMini {

        val entry = httpClient.safeRequest<GetCartMiniDto, ErrorResponse> {
            method = HttpMethod.Post
            url {
                appendPathSegments("cart")
                setBody(item)
            }
        }

        return if (entry is ApiResponse.Success) entry.body else GetCartMiniDto()
    }

    override suspend fun getMiniCart(): GetCartMini {

        val entry = httpClient.safeRequest<GetCartMiniDto, ErrorResponse> {
            method = HttpMethod.Get
            url {
                appendPathSegments("cart/mini")
            }
        }

        return if (entry is ApiResponse.Success) entry.body else GetCartMiniDto()
    }

    override suspend fun getFullCart(): GetCartFull {

        val entry = httpClient.safeRequest<GetCartFullDto, ErrorResponse> {
            method = HttpMethod.Get
            url {
                appendPathSegments("cart")
            }
        }

        return if (entry is ApiResponse.Success) entry.body else GetCartFullDto()
    }


    override suspend fun removeCart(id: Int): GetCartMini {

        val entry = httpClient.safeRequest<GetCartMiniDto, ErrorResponse> {
            method = HttpMethod.Delete
            url {
                appendPathSegments("cart/$id")
            }
        }

        return if (entry is ApiResponse.Success) entry.body else GetCartMiniDto()
    }


}