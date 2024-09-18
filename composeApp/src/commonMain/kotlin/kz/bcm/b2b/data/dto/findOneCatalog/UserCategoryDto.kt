package kz.bcm.b2b.data.dto.findOneCatalog

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.findOneCatalog.UserCategory

@Serializable
data class UserCategoryDto(
    override val id: Int,
    override val id1c: String,
    override val status: Int,
    override val slug: String,
    override val title: String,
    override val text: String?,
    override val photo: String?,
    override val popular: Int,
    override val parentId: String,
    override val discountId: String?,
    override val childCategory: List<UserCategoryDto>?
): UserCategory
