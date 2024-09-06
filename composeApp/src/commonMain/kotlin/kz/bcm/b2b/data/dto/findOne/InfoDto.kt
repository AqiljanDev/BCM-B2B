package kz.bcm.b2b.data.dto.findOne

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.findOne.ChildCategory
import kz.bcm.b2b.domain.data.findOne.Info
import kz.bcm.b2b.domain.data.findOne.ParentCategory

@Serializable
data class InfoDto(
    override val id: Int = 0,
    override val id1c: String = "",
    override val title: String = "",
    override val slug: String = "",
    override val photo: String = "",
    override val parentId: String = "",
    override val parentCategory: ParentCategoryDto? = null,
    override val childCategory: List<ChildCategoryDto> = emptyList()
) : Info
