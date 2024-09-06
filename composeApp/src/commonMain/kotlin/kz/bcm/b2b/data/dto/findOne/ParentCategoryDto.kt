package kz.bcm.b2b.data.dto.findOne

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.findOne.Discount
import kz.bcm.b2b.domain.data.findOne.ParentCategory

@Serializable
data class ParentCategoryDto(
    override val slug: String = "",
    override val title: String = "",
    override val parentCategory: ParentCategoryDto? = null,
    override val discount: DiscountDto? = null
) : ParentCategory
