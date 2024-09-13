package kz.bcm.b2b.domain.data.cart.full

interface GetCartFull {
    val products: List<CartFullProduct>
    val totalCount: Int
}