package kz.bcm.b2b.domain.data.cart.mini

interface Product {
    val id: Int
    val userId: Int
    val prodId: String
    val count: Int
}