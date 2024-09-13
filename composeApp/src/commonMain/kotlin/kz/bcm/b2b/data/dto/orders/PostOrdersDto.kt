package kz.bcm.b2b.data.dto.orders

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.orders.PostOrders

@Serializable
data class PostOrdersDto(
    override val address: String = "",
    override val commentUser: String = "",
    override val userBillId: Int = 0,
    override val deliverId: Int = 0,
    override val discount: String = "",
    override val products: String = ""
) : PostOrders

