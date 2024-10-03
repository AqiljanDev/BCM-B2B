package kz.bcm.b2b.domain.data.sale

import kotlinx.serialization.json.JsonElement

interface GetSaleOne {
    val id: Int
    val title: String
    val slug: String
    val text: String
    val link: String
    val photo: String
    val dateEnd: String
    val products: List<JsonElement>
    val categories: List<JsonElement>
}