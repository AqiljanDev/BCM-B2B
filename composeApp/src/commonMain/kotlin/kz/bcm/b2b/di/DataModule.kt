package kz.bcm.b2b.di

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json
import kz.bcm.b2b.data.datasource.CatalogDataSourceImpl
import kz.bcm.b2b.data.repository.RepositoryImpl
import kz.bcm.b2b.domain.repository.Repository
import kz.bcm.b2b.domain.repository.datasource.CatalogDataSource
import org.koin.dsl.module

val dataModule = module {
//    single {
//        HttpClient(CIO) {
//            // Установка базового URL
//            defaultRequest {
//                url("http://192.168.8.255:4040/api/") // базовый URL
//            }
//
//            // Установка тайм-аутов
//            install(HttpTimeout) {
//                requestTimeoutMillis = 45_000 // 45 секунд
//                connectTimeoutMillis = 45_000 // 45 секунд
//            }
//
//            install(ContentNegotiation) {
//                json(Json {
//                    ignoreUnknownKeys = true
//                    isLenient = true
//                    coerceInputValues = true
//                })
//            }
//
//            // Добавление пользовательских заголовков
//            defaultRequest {
//                header(HttpHeaders.Accept, ContentType.Application.Json)
//                header(HttpHeaders.ContentType, ContentType.Application.Json)
//
//                val token = "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9." +
//                        "eyJpZCI6MiwiZW1haWwiOiJha2lsc2F5ZnVsbGFldjYxQGdtYWlsLm" +
//                        "NvbSIsImNvbXBhbnkiOiJ0ZXN0IiwibWFpbkFkbWluIjoxLCJpYXQi" +
//                        "OjE3MjU0NDcxNzAsImV4cCI6MTcyNjA1MTk3MH0.RidFQiuzKIGCsRyuCa52uulGsBCRYrQibo-Aa8Mdkac"
//                header(HttpHeaders.Authorization, token)
//            }
//
//            // Обработка ошибок с помощью HttpResponseValidator
//            HttpResponseValidator {
//                validateResponse { response ->
//                    // Проверяем HTTP код ответа
//                    when (response.status.value) {
//                        in 300..399 -> throw RedirectResponseException(
//                            response,
//                            "Redirection error"
//                        )
//
//                        in 400..499 -> throw ClientRequestException(response, "Client error")
//                        in 500..599 -> throw ServerResponseException(response, "Server error")
//                        else -> {
//                            println("Unknown error")
//                        }
//                    }
//                }
//
//                // Перехватываем исключения
//                handleResponseExceptionWithRequest { exception, request ->
//                    when (exception) {
//                        is ClientRequestException -> {
//                            // Обработка ошибок клиента (например, 400 Bad Request)
//                            println("Client Error: ${exception.response.status.description}")
//                        }
//
//                        is ServerResponseException -> {
//                            // Обработка серверных ошибок (например, 500 Internal Server Error)
//                            println("Server Error: ${exception.response.status.description}")
//                        }
//
//                        is RedirectResponseException -> {
//                            // Обработка ошибок перенаправления (например, 301 Moved Permanently)
//                            println("Redirect Error: ${exception.response.status.description}")
//                        }
//
//                        else -> {
//                            // Обработка других исключений
//                            println("Other Error: ${exception.message}")
//                        }
//                    }
//                }
//            }
//
//            // Логгирование запросов и ответов для отладки
//            install(Logging) {
//                level = LogLevel.BODY
//            }
//        }
//    }

    single<Repository> {
        RepositoryImpl(get())
    }


    single<CatalogDataSource> {
        CatalogDataSourceImpl()
    }

}