package kz.bcm.b2b.domain.data.findOne

interface Discount {
    val id: Int
    val type: Int
    val value: Int
    val userId: String?
    val userCatId: String?
    val productId: String?
    val categoryId: String?
}
