package kz.bcm.b2b.data.dto.findOneCatalog

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.findOneCatalog.UserCategory
import kz.bcm.b2b.domain.data.findOneCatalog.UserDiscount

@Serializable
data class UserDiscountDto(
    override val id: Int,
    override val type: Int,
    override val value: Int,
    override val userId: Int,
    override val userCatId: String,
    override val productId: String?,
    override val categoryId: String?,
    override val userCategory: UserCategoryDto?
) : UserDiscount
