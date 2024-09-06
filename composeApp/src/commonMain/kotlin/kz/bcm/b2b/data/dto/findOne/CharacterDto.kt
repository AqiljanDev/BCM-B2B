package kz.bcm.b2b.data.dto.findOne

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.findOne.Character

@Serializable
data class CharacterDto (
    override val id: Int = 0,
    override val id1c: String = "",
    override val title: String = ""
) : Character
