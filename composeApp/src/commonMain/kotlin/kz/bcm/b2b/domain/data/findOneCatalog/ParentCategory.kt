package kz.bcm.b2b.domain.data.findOneCatalog

interface ParentCategory {
    val slug: String
    val title: String
    val parentCategory: ParentCategory?
    val discount: Discount?
}
