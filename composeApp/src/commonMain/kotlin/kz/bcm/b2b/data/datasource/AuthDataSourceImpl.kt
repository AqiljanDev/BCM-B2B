import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.client.statement.bodyAsText
import io.ktor.http.HttpStatusCode

import kotlinx.serialization.SerializationException
import kotlinx.serialization.json.Json
import kz.bcm.b2b.data.dto.auth.AccessTokenDto
import kz.bcm.b2b.di.ErrorResponse
import kz.bcm.b2b.di.ErrorResponseListM
import kz.bcm.b2b.domain.data.auth.AccessToken
import kz.bcm.b2b.domain.data.auth.login.PostLogin
import kz.bcm.b2b.domain.data.auth.passwordCodeSend.PasswordSend
import kz.bcm.b2b.domain.data.auth.register.PostRegistration
import kz.bcm.b2b.domain.repository.datasource.AuthDataSource
import kz.bcm.b2b.presentation.other.ApiResponse
import kz.bcm.b2b.presentation.other.safeRequest

class AuthDataSourceImpl(
    private val httpClient: HttpClient
) : AuthDataSource {

    override suspend fun login(body: PostLogin): AccessToken {

        val entry = httpClient.post("auth/login") {
            setBody(body)
        }

        try {
            println("status: ${entry.status} == ${HttpStatusCode.OK}")
            if (entry.status.value in 201..299) {
                return entry.body<AccessTokenDto>()

            } else {
                val errorBody = entry.bodyAsText() // Get the raw response as a string
                println("Error Response: $errorBody") // Log the raw response for debugging

                // Now try to deserialize it into ErrorResponse
                try {
                    val errorResponse = Json.decodeFromString<ErrorResponse>(errorBody)
                    throw Exception(errorResponse.message ?: "Unknown error")
                } catch (serializationException: SerializationException) {
                    // If deserialization fails, handle the error gracefully
                    val err = Json.decodeFromString<ErrorResponseListM>(errorBody)
                    throw Exception(err.message.joinToString(", "))
                }
            }
        } catch (e: Exception) {
            println("Exc === ${e.message}")
            throw Exception(e.message)
        }

    }

    override suspend fun registration(body: PostRegistration): AccessToken {
        val entry = httpClient.post("auth/registration") {
            setBody(body)
        }

        try {
            println("status: ${entry.status} == ${HttpStatusCode.OK}")
            if (entry.status.value in 201..299) {
                return entry.body<AccessTokenDto>()

            } else {
                val errorBody = entry.bodyAsText() // Get the raw response as a string
                println("Error Response: $errorBody") // Log the raw response for debugging

                // Now try to deserialize it into ErrorResponse
                try {
                    val errorResponse = Json.decodeFromString<ErrorResponse>(errorBody)
                    throw Exception(errorResponse.message ?: "Unknown error")
                } catch (serializationException: SerializationException) {
                    // If deserialization fails, handle the error gracefully
                    val err = Json.decodeFromString<ErrorResponseListM>(errorBody)
                    throw Exception(err.message.joinToString(", "))
                }
            }
        } catch (e: Exception) {
            println("Exc === ${e.message}")
            throw Exception(e.message)
        }
    }


    override suspend fun passwordCodeSend(passwordSend: PasswordSend) {
        try {
            val entry = httpClient.post("auth/password/code/send") {
                setBody(passwordSend)
            }

            if (entry.status.value !in 201..299) {
                val errorBody = entry.bodyAsText()
                println("Error Response: $errorBody")

                val errorResponse = Json.decodeFromString<ErrorResponse>(errorBody)
                throw Exception(errorResponse.message)
            }

        } catch (e: Exception) {
            // Обработка исключений
            println("Error sending password code: ${e.message}")
            throw e // Пробрасываем исключение для дальнейшей обработки
        }
    }


}