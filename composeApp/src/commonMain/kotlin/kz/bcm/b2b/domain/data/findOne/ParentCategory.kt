package kz.bcm.b2b.domain.data.findOne

interface ParentCategory {
    val slug: String
    val title: String
    val parentCategory: ParentCategory?
    val discount: Discount?
}
