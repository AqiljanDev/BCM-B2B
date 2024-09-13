package kz.bcm.b2b.domain.data.wishlistAndCompare.wishlist

import kz.bcm.b2b.domain.data.findOneCatalog.Product

interface WishListFull {
    val id: Int
    val userId: Int
    val prodId: String
    val product: Product
}