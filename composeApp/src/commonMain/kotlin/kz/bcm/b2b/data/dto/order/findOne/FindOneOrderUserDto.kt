package kz.bcm.b2b.data.dto.order.findOne

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.order.findOne.FindOneOrderUser

@Serializable
data class FindOneOrderUserDto(
    override val id: Int = 0,
    override val email: String = "",
    override val statusOn: Int = 0,
    override val statusFill: Int = 0,
    override val mainAdmin: Int = 0,
    override val role: String = "",
    override val password: String = "",
    override val fio: String? = null,
    override val phone: String = "",
    override val company: String = "",
    override val type: String = "",
    override val area: String = "",
    override val site: String = "",
    override val bin: String = "",
    override val address: String = "",
    override val bik: String = "",
    override val bank: String = "",
    override val iik: String = ""
) : FindOneOrderUser
