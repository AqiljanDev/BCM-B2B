package kz.bcm.b2b.data.dto.bill

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.bill.BillBody

@Serializable
data class BillBodyDto(
    override val code: String = "",
    override val bank: String = "",
    override val kbe: String = ""
) : BillBody