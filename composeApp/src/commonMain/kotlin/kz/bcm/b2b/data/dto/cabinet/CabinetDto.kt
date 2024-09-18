package kz.bcm.b2b.data.dto.cabinet

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.cabinet.Cabinet

@Serializable
data class CabinetDto(
    override var phone: String = "",
    override var company: String = "",
    override var type: String = "",
    override var area: String = "",
    override var site: String = "",
    override var bin: String = "",
    override var address: String = "",
    override var bik: String = "",
    override var bank: String = "",
    override var iik: String = ""
) : Cabinet
