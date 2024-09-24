package kz.bcm.b2b.presentation.other

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.request
import io.ktor.utils.io.errors.IOException
import kotlinx.serialization.SerializationException


fun <T> List<T>.toggleItem(item: T): List<T> {
    val newList = this.toMutableList()
    if (this.contains(item)) {
        newList.remove(item)
    } else {
        newList.add(item)
    }

    return newList.toList()
}


suspend inline fun <reified T, reified E> HttpClient.safeRequest(
    block: HttpRequestBuilder.() -> Unit,
): ApiResponse<T, E> =
    try {
        val response = request { block() }
        val sss = response.body<T>()

        println("safeRequest = success: $sss")
        ApiResponse.Success(sss)
    } catch (e: ClientRequestException) {

        println("safeRequest = ClientRequestException: ${e.message}")
        ApiResponse.Error.HttpError(e.response.status.value, e.errorBody())
    } catch (e: ServerResponseException) {

        println("safeRequest = ServerResponseException: ${e.message}")
        ApiResponse.Error.HttpError(e.response.status.value, e.errorBody())
    } catch (e: IOException) {

        println("safeRequest = IOException: ${e.message}")
        ApiResponse.Error.NetworkError
    } catch (e: SerializationException) {

        println("safeRequest = SerializationException: ${e.message}")
        ApiResponse.Error.SerializationError
    }


suspend inline fun <reified E> ResponseException.errorBody(): E? =
    try {

        response.body()
    } catch (e: SerializationException) {
        println("safeRequest -> errorBody = SerializationException: ${e.message}")
        null
    }


sealed class ApiResponse<out T, out E> {
    /**
     * Represents successful network responses (2xx).
     */
    data class Success<T>(val body: T) : ApiResponse<T, Nothing>()

    sealed class Error<E> : ApiResponse<Nothing, E>() {
        /**
         * Represents server (50x) and client (40x) errors.
         */
        data class HttpError<E>(val code: Int, val errorBody: E?) : Error<E>()

        /**
         * Represent IOExceptions and connectivity issues.
         */
        object NetworkError : Error<Nothing>()

        /**
         * Represent SerializationExceptions.
         */
        object SerializationError : Error<Nothing>()
    }
}
