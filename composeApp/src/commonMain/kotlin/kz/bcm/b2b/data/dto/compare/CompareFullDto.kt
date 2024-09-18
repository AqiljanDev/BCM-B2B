package kz.bcm.b2b.data.dto.compare

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.compare.CharactersToCompare
import kz.bcm.b2b.domain.data.compare.CompareFull
import kz.bcm.b2b.domain.data.compare.Product
import kz.bcm.b2b.domain.data.compare.ProductNested

@Serializable
data class CompareFullDto(
    override val characters: List<CharactersToCompareDto> = emptyList(),
    override val products: List<ProductDto> = emptyList()
) : CompareFull
