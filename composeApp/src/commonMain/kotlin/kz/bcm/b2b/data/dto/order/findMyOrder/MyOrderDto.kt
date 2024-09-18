package kz.bcm.b2b.data.dto.order.findMyOrder

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.order.findMyOrder.MyOrder

@Serializable
data class MyOrderDto(
    override val id: Int = 0,
    override val address: String = "",
    override val commentUser: String = "",
    override val commentAdmin: String? = null,
    override val date: String = "",
    override val userBillId: Int = 0,
    override val deliverId: Int = 0,
    override val orderStatusId: Int = 0,
    override val usersId: Int = 0,
    override val adminId: String? = null,
    override val userBill: MyOrderUserBillDto = MyOrderUserBillDto(),
    override val orderStatus: MyOrderStatusDto = MyOrderStatusDto(),
    override val products: List<MyOrderProductDto> = emptyList()
): MyOrder
