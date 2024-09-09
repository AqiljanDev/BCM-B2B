package kz.bcm.b2b.data.dto.cart.mini

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.cart.mini.Product

@Serializable
data class ProductDto(
    override val id: Int = 0,
    override val userId: Int = 0,
    override val prodId: String = "",
    override val count: Int = 0
): Product
