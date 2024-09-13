package kz.bcm.b2b.data.dto.cart.full

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.cart.full.GetCartFull

@Serializable
data class GetCartFullDto(
    override val products: List<CartFullProductDto> = emptyList(),
    override val totalCount: Int = 0
) : GetCartFull
