package kz.bcm.b2b.data.dto.findOneProduct

import kotlinx.serialization.Serializable
import kz.bcm.b2b.data.dto.findOneCatalog.CategoriesDto
import kz.bcm.b2b.data.dto.findOneCatalog.CharactersToProductsDto
import kz.bcm.b2b.data.dto.findOneCatalog.DiscountDto
import kz.bcm.b2b.data.dto.findOneCatalog.GalleryItemDto
import kz.bcm.b2b.data.dto.findOneCatalog.ProductDto
import kz.bcm.b2b.domain.data.findOneCatalog.Categories
import kz.bcm.b2b.domain.data.findOneCatalog.CharactersToProducts
import kz.bcm.b2b.domain.data.findOneCatalog.Discount
import kz.bcm.b2b.domain.data.findOneCatalog.GalleryItem
import kz.bcm.b2b.domain.data.findOneCatalog.Product
import kz.bcm.b2b.domain.data.findOneProduct.Brands
import kz.bcm.b2b.domain.data.findOneProduct.FindOneProduct

@Serializable
data class FindOneProductDto(
    override val id: Int = 0,
    override val id1c: String = "",
    override val slug: String = "",
    override val article: String = "",
    override val title: String = "",
    override val titleFull: String = "",
    override val status: Int = 0,
    override val price: Int = 0,
    override val count: Int = 0,
    override val desc: String = "",
    override val youtubeLink: String? = null,
    override val categoriesId: String = "",
    override val brandsId: String? = null,
    override val discount: DiscountDto? = null,
    override val gallery: List<GalleryItemDto> = emptyList(),
    override val categories: CategoriesDto = CategoriesDto(),
    override val charactersToProducts: List<CharactersToProductsDto> = emptyList(),
    override val brands: BrandsDto? = null,
    override val files: List<String> = emptyList(),
    override val other: List<ProductDto> = emptyList()
) : FindOneProduct
