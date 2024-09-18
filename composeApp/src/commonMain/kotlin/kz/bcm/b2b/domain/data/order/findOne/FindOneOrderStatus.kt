package kz.bcm.b2b.domain.data.order.findOne

interface FindOneOrderStatus {
    val id: Int
    val name: String
    val default: Int
    val endStatus: Int
}
