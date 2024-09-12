package kz.bcm.b2b.data.dto.findOneCatalog

import kz.bcm.b2b.domain.data.findOneCatalog.Catalog
import kotlinx.serialization.Serializable

@Serializable
data class CatalogDto(
    override val info: InfoDto = InfoDto(),
    override val products: List<ProductDto> = emptyList()
) : Catalog
