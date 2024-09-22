package kz.bcm.b2b.data.dto.auth.login

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.auth.login.PostLogin


@Serializable
data class PostLoginDto(
    override var email: String = "",
    override var password: String = ""
) : PostLogin