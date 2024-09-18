package kz.bcm.b2b.data.dto.compare

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.compare.Product
import kz.bcm.b2b.domain.data.compare.ProductNested

@Serializable
data class ProductDto(
    override val id: Int,
    override val product: ProductNestedDto
) : Product
