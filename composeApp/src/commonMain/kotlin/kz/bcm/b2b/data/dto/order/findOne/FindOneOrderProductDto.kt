package kz.bcm.b2b.data.dto.order.findOne

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.order.findOne.FindOneOrderProduct
import kz.bcm.b2b.domain.data.order.findOne.FindOneOrderProductPreview

@Serializable
data class FindOneOrderProductDto(
    override val id: Int = 0,
    override val id1c: String = "",
    override val productId: Int = 0,
    override val article: String = "",
    override val title: String = "",
    override val preview: FindOneOrderProductPreviewDto? = null,
    override val discount: String = "",
    override val price: Int = 0,
    override val count: Int = 0,
    override val orderId: Int = 0
) : FindOneOrderProduct
