package kz.bcm.b2b.domain.data.order.findOne

import kz.bcm.b2b.domain.data.order.findMyOrder.MyOrder

interface FindOneProduct {
    val id: Int
    val address: String
    val commentUser: String
    val commentAdmin: String?
    val date: String
    val userBillId: Int
    val deliverId: Int
    val orderStatusId: Int
    val usersId: Int
    val adminId: Int?
    val products: List<FindOneOrderProduct>
    val orderStatus: FindOneOrderStatus
    val users: FindOneOrderUser
    val userBill: FindOneOrderUserBill
    val admins: FindOneOrderUser?
}