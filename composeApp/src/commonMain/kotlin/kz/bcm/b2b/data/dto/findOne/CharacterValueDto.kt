package kz.bcm.b2b.data.dto.findOne

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.findOne.CharacterValue

@Serializable
data class CharacterValueDto (
    override val id: Int = 0,
    override val id1c: String = "",
    override val title: String = "",
    override val characterId: String = ""
) : CharacterValue
