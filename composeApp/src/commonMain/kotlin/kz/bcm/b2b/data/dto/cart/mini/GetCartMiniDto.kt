package kz.bcm.b2b.data.dto.cart.mini

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.cart.mini.GetCartMini

@Serializable
data class GetCartMiniDto(
    override val products: List<ProductDto> = listOf(),
    override val totalCount: Int = 0
): GetCartMini
