package kz.bcm.b2b.data.dto.sale

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.sale.GetSaleAll

@Serializable
data class GetSaleAllDto(
    override val id: Int = 0,
    override val title: String = "",
    override val slug: String = "",
    override val text: String = "",
    override val link: String = "",
    override val photo: String = "",
    override val dateEnd: String = ""
): GetSaleAll
