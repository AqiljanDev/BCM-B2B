package kz.bcm.b2b.domain.data.findOne

interface UserDiscount {
    val id: Int
    val type: Int
    val value: Int
    val userId: Int
    val userCatId: String
    val productId: String?
    val categoryId: String?
    val userCategory: UserCategory?
}
