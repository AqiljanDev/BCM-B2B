package kz.bcm.b2b.data.dto.order.findOne

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.order.findOne.FindOneOrderStatus

@Serializable
data class FindOneOrderStatusDto(
    override val id: Int = 0,
    override val name: String = "",
    override val default: Int = 0,
    override val endStatus: Int = 0
) : FindOneOrderStatus
