package kz.bcm.b2b.data.dto.cart.event

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.cart.event.PostCart

@Serializable
data class PostCartDto(
    override val prodId: String,
    override val count: Int
): PostCart
