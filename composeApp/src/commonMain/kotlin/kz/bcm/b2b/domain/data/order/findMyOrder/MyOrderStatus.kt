package kz.bcm.b2b.domain.data.order.findMyOrder

interface MyOrderStatus {
    val id: Int
    val name: String
    val default: Int
    val endStatus: Int
}
