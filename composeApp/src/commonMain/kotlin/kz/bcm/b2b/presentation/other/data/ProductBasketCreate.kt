package kz.bcm.b2b.presentation.other.data

import kotlinx.serialization.Serializable

@Serializable
data class ProductBasketCreate(
    val id: Int,
    val c: Int,
    val p: Int,
    val d: String
)
