package kz.bcm.b2b.data.dto.findOneCatalog

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.findOneCatalog.CharactersToProducts

@Serializable
data class CharactersToProductsDto (
    override val id: Int = 0,
    override val order: Int = 0,
    override val characterId: String = "",
    override val characterValueId: String = "",
    override val productId: String = "",
    override val character: CharacterDto = CharacterDto(),
    override val characterValue: CharacterValueDto = CharacterValueDto()
) : CharactersToProducts
