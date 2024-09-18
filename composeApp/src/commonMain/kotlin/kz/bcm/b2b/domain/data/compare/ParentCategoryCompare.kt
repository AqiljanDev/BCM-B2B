package kz.bcm.b2b.domain.data.compare

import kz.bcm.b2b.domain.data.findOneCatalog.Discount

interface ParentCategoryCompare {
    val slug: String
    val title: String
    val parentCategory: ParentCategoryCompare?
    val discount: Discount?
}
