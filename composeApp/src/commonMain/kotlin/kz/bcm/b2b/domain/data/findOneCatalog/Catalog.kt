package kz.bcm.b2b.domain.data.findOneCatalog

interface Catalog {
    val info: Info
    val products: List<Product>
}