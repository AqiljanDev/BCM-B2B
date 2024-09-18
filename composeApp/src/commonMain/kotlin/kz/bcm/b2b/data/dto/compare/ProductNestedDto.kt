package kz.bcm.b2b.data.dto.compare

import kotlinx.serialization.Serializable
import kz.bcm.b2b.data.dto.findOneCatalog.DiscountDto
import kz.bcm.b2b.domain.data.compare.ProductNested

@Serializable
data class ProductNestedDto(
    override val id1c: String = "",
    override val title: String = "",
    override val slug: String = "",
    override val photo: String? = null,
    override val count: Int = 0,
    override val price: Int = 0,
    override val discount: DiscountDto? = null,
    override val categories: CategoryCompareDto,
    override val characters: List<String> = emptyList(),
    override val categoriesId: String = ""
) : ProductNested
