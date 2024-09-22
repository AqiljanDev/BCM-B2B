package kz.bcm.b2b.data.dto.auth.register

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.auth.register.PostRegistration


@Serializable
data class PostRegistrationDto(
    override val email: String = "",
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
) : PostRegistration
