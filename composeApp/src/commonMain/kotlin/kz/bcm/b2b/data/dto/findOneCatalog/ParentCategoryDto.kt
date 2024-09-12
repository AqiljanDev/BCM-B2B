package kz.bcm.b2b.data.dto.findOneCatalog

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.findOneCatalog.ParentCategory

@Serializable
data class ParentCategoryDto(
    override val slug: String = "",
    override val title: String = "",
    override val parentCategory: ParentCategoryDto? = null,
    override val discount: DiscountDto? = null
) : ParentCategory
