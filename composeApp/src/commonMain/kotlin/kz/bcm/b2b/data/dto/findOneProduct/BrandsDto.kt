package kz.bcm.b2b.data.dto.findOneProduct

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.findOneProduct.Brands

@Serializable
data class BrandsDto(
    override val id: Int = 0,
    override val id1c: String = "",
    override val title: String = "",
    override val slug: String = "",
    override val photo: String? = null
) : Brands
