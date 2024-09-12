package kz.bcm.b2b.data.dto.findOneCatalog

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.findOneCatalog.Categories

@Serializable
data class CategoriesDto (
    override val id: Int = 0,
    override val id1c: String = "",
    override val status: Int = 0,
    override val slug: String = "",
    override val title: String = "",
    override val text: String? = null,
    override val photo: String? = null,
    override val popular: Int = 0,
    override val parentId: String = "",
    override val discountId: String? = null,
    override val discount: DiscountDto? = null,
    override val parentCategory: ParentCategoryDto? = null
) : Categories
