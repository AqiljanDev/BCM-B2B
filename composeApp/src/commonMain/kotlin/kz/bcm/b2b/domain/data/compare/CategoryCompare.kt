package kz.bcm.b2b.domain.data.compare

import kz.bcm.b2b.domain.data.findOneCatalog.Discount
import kz.bcm.b2b.domain.data.findOneCatalog.ParentCategory

interface CategoryCompare {
    val discount: Discount?
    val parentCategory: ParentCategory?
}
