package kz.bcm.b2b.domain.data.compare

import kz.bcm.b2b.domain.data.findOneCatalog.Discount

interface CategoryCompare {
    val discount: Discount?
    val parentCategory: ParentCategoryCompare?
}
