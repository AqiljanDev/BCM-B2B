package kz.bcm.b2b.data.dto.compare

import kotlinx.serialization.Serializable
import kz.bcm.b2b.data.dto.findOneCatalog.DiscountDto
import kz.bcm.b2b.domain.data.compare.CategoryCompare
import kz.bcm.b2b.domain.data.compare.ParentCategoryCompare
import kz.bcm.b2b.domain.data.findOneCatalog.Discount

@Serializable
data class CategoryCompareDto(
    override val discount: DiscountDto? = null,
    override val parentCategory: ParentCategoryCompareDto? = null
) : CategoryCompare
