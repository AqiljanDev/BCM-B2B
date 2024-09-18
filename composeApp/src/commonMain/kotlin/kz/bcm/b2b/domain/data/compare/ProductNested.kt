package kz.bcm.b2b.domain.data.compare

import kz.bcm.b2b.domain.data.findOneCatalog.Discount

interface ProductNested {
    val id1c: String
    val title: String
    val slug: String
    val photo: String?
    val count: Int
    val price: Int
    val discount: Discount?
    val categories: CategoryCompare
    val characters: List<String>
    val categoriesId: String
}
