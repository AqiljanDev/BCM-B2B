package kz.bcm.b2b.data.dto.order.findOne

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.order.findOne.FindOneOrderProductPreview

@Serializable
data class FindOneOrderProductPreviewDto(
    override val type: String = "",
    override val data: List<Int> = emptyList()
) :FindOneOrderProductPreview
