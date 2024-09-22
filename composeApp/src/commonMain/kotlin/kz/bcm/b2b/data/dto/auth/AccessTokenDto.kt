package kz.bcm.b2b.data.dto.auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kz.bcm.b2b.domain.data.auth.AccessToken


@Serializable
data class AccessTokenDto(
    @SerialName(value = "access_token")
    override val accessToken: String
) : AccessToken
