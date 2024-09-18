package kz.bcm.b2b.domain.data.order.findMyOrder

interface MyOrderUserBill {
    val id: Int
    val bank: String
    val code: String
    val kbe: String
    val status: Int
    val usersId: Int
}
