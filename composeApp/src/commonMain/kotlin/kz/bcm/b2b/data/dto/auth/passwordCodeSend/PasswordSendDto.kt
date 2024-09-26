package kz.bcm.b2b.data.dto.auth.passwordCodeSend

import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.auth.passwordCodeSend.PasswordSend


@Serializable
data class PasswordSendDto(
    override val email: String = ""
) : PasswordSend
