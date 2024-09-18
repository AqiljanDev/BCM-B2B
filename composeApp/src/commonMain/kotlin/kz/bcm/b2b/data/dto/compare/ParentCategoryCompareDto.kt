package kz.bcm.b2b.data.dto.compare

import kotlinx.serialization.Serializable
import kz.bcm.b2b.data.dto.findOneCatalog.DiscountDto
import kz.bcm.b2b.domain.data.compare.ParentCategoryCompare
import kz.bcm.b2b.domain.data.findOneCatalog.Discount

@Serializable
data class ParentCategoryCompareDto(
    override val slug: String,
    override val title: String,
    override val parentCategory: ParentCategoryCompareDto?,
    override val discount: DiscountDto?
) : ParentCategoryCompare
