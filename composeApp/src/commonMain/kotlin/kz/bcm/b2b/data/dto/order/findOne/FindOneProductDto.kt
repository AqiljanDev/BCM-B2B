package kz.bcm.b2b.data.dto.order.findOne

import kotlinx.serialization.Serializable
import kz.bcm.b2b.data.dto.order.findMyOrder.MyOrderProductDto
import kz.bcm.b2b.data.dto.order.findMyOrder.MyOrderStatusDto
import kz.bcm.b2b.data.dto.order.findMyOrder.MyOrderUserBillDto
import kz.bcm.b2b.domain.data.order.findMyOrder.MyOrderProduct
import kz.bcm.b2b.domain.data.order.findMyOrder.MyOrderStatus
import kz.bcm.b2b.domain.data.order.findMyOrder.MyOrderUserBill
import kz.bcm.b2b.domain.data.order.findOne.FindOneOrderProduct
import kz.bcm.b2b.domain.data.order.findOne.FindOneOrderStatus
import kz.bcm.b2b.domain.data.order.findOne.FindOneOrderUser
import kz.bcm.b2b.domain.data.order.findOne.FindOneOrderUserBill
import kz.bcm.b2b.domain.data.order.findOne.FindOneProduct

@Serializable
data class FindOneProductDto(
    override val id: Int = 0,
    override val address: String = "",
    override val commentUser: String = "",
    override val commentAdmin: String? = null,
    override val date: String = "",
    override val userBillId: Int = 0,
    override val deliverId: Int = 0,
    override val orderStatusId: Int = 0,
    override val usersId: Int = 0,
    override val adminId: Int? = null,
    override val products: List<FindOneOrderProductDto> = emptyList(),
    override val orderStatus: FindOneOrderStatusDto = FindOneOrderStatusDto(),
    override val userBill: FindOneOrderUserBillDto = FindOneOrderUserBillDto(),
    override val users: FindOneOrderUserDto = FindOneOrderUserDto(),
    override val admins: FindOneOrderUserDto? = null
) : FindOneProduct
