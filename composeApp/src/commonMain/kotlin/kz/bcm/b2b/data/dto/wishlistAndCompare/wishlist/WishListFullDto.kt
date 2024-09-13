package kz.bcm.b2b.data.dto.wishlistAndCompare.wishlist

import kotlinx.serialization.Serializable
import kz.bcm.b2b.data.dto.findOneCatalog.ProductDto
import kz.bcm.b2b.domain.data.wishlistAndCompare.wishlist.WishListFull

@Serializable
data class WishListFullDto(
    override val id: Int = 0,
    override val userId: Int = 0,
    override val prodId: String = "",
    override val product: ProductDto = ProductDto()
) : WishListFull

