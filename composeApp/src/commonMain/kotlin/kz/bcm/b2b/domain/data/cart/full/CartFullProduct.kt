package kz.bcm.b2b.domain.data.cart.full

import kz.bcm.b2b.domain.data.findOneCatalog.Product

interface CartFullProduct {
    val id: Int
    val userId: Int
    val prodId: String
    val count: Int
    val product: Product
}
