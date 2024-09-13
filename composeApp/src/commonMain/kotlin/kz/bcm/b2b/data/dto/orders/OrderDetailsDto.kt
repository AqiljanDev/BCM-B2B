package kz.bcm.b2b.data.dto.orders

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.orders.OrderDetails

@Serializable
data class OrderDetailsDto(
    override val id: Int = 0,
    override val address: String = "",
    override val commentUser: String = "",
    override val commentAdmin: String? = null,
    override val date: String = "",
    override val userBillId: Int = 0,
    override val deliverId: Int = 0,
    override val orderStatusId: Int = 0,
    override val usersId: Int = 0,
    override val adminId: Int? = null
) : OrderDetails
