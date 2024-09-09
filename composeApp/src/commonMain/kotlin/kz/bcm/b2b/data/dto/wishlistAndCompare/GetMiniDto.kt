package kz.bcm.b2b.data.dto.wishlistAndCompare

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.wishlistAndCompare.GetMini

@Serializable
data class GetMiniDto(
    override val prodId: String
) : GetMini
