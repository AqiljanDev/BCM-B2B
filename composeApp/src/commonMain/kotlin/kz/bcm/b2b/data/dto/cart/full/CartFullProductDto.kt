package kz.bcm.b2b.data.dto.cart.full

import kotlinx.serialization.Serializable
import kz.bcm.b2b.data.dto.findOneCatalog.ProductDto
import kz.bcm.b2b.domain.data.cart.full.CartFullProduct

@Serializable
data class CartFullProductDto(
    override val id: Int = 0,
    override val userId: Int = 0,
    override val prodId: String = "",
    override val count: Int = 0,
    override val product: ProductDto = ProductDto()
) : CartFullProduct

