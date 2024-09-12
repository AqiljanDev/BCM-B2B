package kz.bcm.b2b.domain.data.findOneCatalog

interface Product {
    val id: Int
    val id1c: String
    val slug: String
    val article: String
    val title: String
    val titleFull: String
    val status: Int
    val price: Int
    val count: Int
    val desc: String
    val youtubeLink: String?
    val categoriesId: String
    val brandsId: String?
    val discount: Discount?
    val gallery: List<GalleryItem>
    val categories: Categories
    val charactersToProducts: List<CharactersToProducts>
}
