package kz.bcm.b2b.domain.data.findOne

interface Catalog {
    val info: Info
    val products: List<Product>
}