package kz.bcm.b2b.domain.data.cart.mini

interface GetCartMini {
    val products: List<Product>
    val totalCount: Int
}