package kz.bcm.b2b.data.dto.findOneCatalog

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.findOneCatalog.Discount

@Serializable
data class DiscountDto (
    override val id: Int = 0,
    override val type: Int = 0,
    override val value: Int = 0,
    override val userId: String? = null,
    override val userCatId: String? = null,
    override val productId: String? = null,
    override val categoryId: String? = null
) : Discount
