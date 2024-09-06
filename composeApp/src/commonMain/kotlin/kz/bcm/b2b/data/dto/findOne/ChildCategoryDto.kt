package kz.bcm.b2b.data.dto.findOne

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.findOne.ChildCategory

@Serializable
data class ChildCategoryDto(
    override val title: String = "",
    override val slug: String = ""
) : ChildCategory
