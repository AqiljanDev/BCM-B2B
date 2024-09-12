package kz.bcm.b2b.data.dto.findOneCatalog

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.findOneCatalog.GalleryItem

@Serializable
data class GalleryItemDto (
    override val id: Int = 0,
    override val photo: String = "",
    override val order: Int = 0,
    override val productsId: String = ""
) : GalleryItem
