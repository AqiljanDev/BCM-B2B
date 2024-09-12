package kz.bcm.b2b.data.dto.collectCharacters

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.collectCharacters.CharacterItem
import kz.bcm.b2b.domain.data.collectCharacters.CharacterValue

@Serializable
data class CharacterItemDto(
    override val id: Int = 0,
    override val id1c: String = "",
    override val title: String = "",
    override val characters: List<CharacterValueDto> = emptyList()
) : CharacterItem
