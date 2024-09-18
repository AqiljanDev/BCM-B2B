package kz.bcm.b2b.data.dto.compare

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.compare.CharactersToCompare


@Serializable
data class CharactersToCompareDto(
    override val id: Int = 0,
    override val id1c: String = "",
    override val title: String = ""
) : CharactersToCompare
