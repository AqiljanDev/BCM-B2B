package kz.bcm.b2b.domain.data.orders

interface PostOrders {
    val address: String
    val commentUser: String
    val userBillId: Int
    val deliverId: Int
    val discount: String
    val products: String
}
