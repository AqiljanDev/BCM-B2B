package kz.bcm.b2b.domain.data.order.findOne

interface FindOneOrderUserBill {
    val id: Int
    val bank: String
    val code: String
    val kbe: String
    val status: Int
    val usersId: Int
}