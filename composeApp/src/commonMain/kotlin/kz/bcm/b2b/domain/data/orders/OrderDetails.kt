package kz.bcm.b2b.domain.data.orders

interface OrderDetails {
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
}