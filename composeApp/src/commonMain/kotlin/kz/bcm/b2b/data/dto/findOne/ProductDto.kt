package kz.bcm.b2b.data.dto.findOne

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.findOne.Categories
import kz.bcm.b2b.domain.data.findOne.CharactersToProducts
import kz.bcm.b2b.domain.data.findOne.Discount
import kz.bcm.b2b.domain.data.findOne.GalleryItem
import kz.bcm.b2b.domain.data.findOne.Product

@Serializable
data class ProductDto (
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
    override val charactersToProducts: List<CharactersToProductsDto> = emptyList()
) : Product
