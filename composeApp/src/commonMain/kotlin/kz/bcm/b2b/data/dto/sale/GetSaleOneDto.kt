package kz.bcm.b2b.data.dto.sale

import kotlinx.serialization.Serializable
import kotlinx.serialization.json.JsonElement
import kz.bcm.b2b.domain.data.sale.GetSaleOne

@Serializable
data class GetSaleOneDto(
    override val id: Int = 0,
    override val title: String = "",
    override val slug: String = "",
    override val text: String = "",
    override val link: String = "",
    override val photo: String = "",
    override val dateEnd: String = "",
    override val products: List<JsonElement> = listOf(),
    override val categories: List<JsonElement> = listOf()
) : GetSaleOne
