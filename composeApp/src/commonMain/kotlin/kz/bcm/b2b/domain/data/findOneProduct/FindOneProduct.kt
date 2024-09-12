package kz.bcm.b2b.domain.data.findOneProduct

import kz.bcm.b2b.domain.data.findOneCatalog.Product

interface FindOneProduct : Product {
    val brands: Brands?
    val files: List<String>
    val other: List<Product>
}