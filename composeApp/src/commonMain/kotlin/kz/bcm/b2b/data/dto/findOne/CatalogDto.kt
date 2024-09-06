package kz.bcm.b2b.data.dto.findOne

import kz.bcm.b2b.domain.data.findOne.Catalog
import kz.bcm.b2b.domain.data.findOne.Info
import kz.bcm.b2b.domain.data.findOne.Product
import kotlinx.serialization.Serializable

@Serializable
data class CatalogDto(
    override val info: InfoDto = InfoDto(),
    override val products: List<ProductDto> = emptyList()
) : Catalog
