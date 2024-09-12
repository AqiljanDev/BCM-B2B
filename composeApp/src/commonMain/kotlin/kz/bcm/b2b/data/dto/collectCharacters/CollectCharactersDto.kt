package kz.bcm.b2b.data.dto.collectCharacters

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.collectCharacters.CharacterItem
import kz.bcm.b2b.domain.data.collectCharacters.CollectCharacters

@Serializable
data class CollectCharactersDto(
    override val pages: Int = 0,
    override val characters: List<CharacterItemDto> = emptyList()
) : CollectCharacters
