package kz.bcm.b2b.di

import AuthDataSourceImpl
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.engine.cio.CIO
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.plugins.HttpResponseValidator
import io.ktor.client.plugins.HttpTimeout
import io.ktor.client.plugins.RedirectResponseException
import io.ktor.client.plugins.ResponseException
import io.ktor.client.plugins.ServerResponseException
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.header
import io.ktor.client.statement.bodyAsText
import io.ktor.http.ContentType
import io.ktor.http.HttpHeaders
import io.ktor.serialization.kotlinx.json.json
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json
import kz.bcm.b2b.data.datasource.CatalogDataSourceImpl
import kz.bcm.b2b.data.datasource.ProductActionsDataSourceImpl
import kz.bcm.b2b.data.datasource.ProductsDataSourceImpl
import kz.bcm.b2b.data.datasource.ProfileActionsDataSourceImpl
import kz.bcm.b2b.data.repository.RepositoryImpl
import kz.bcm.b2b.domain.repository.Repository
import kz.bcm.b2b.domain.repository.datasource.AuthDataSource
import kz.bcm.b2b.domain.repository.datasource.CatalogDataSource
import kz.bcm.b2b.domain.repository.datasource.ProductActionsDataSource
import kz.bcm.b2b.domain.repository.datasource.ProductsDataSource
import kz.bcm.b2b.domain.repository.datasource.ProfileActionsDataSource
import kz.bcm.b2b.sharedPref.URL
import kz.bcm.b2b.sharedPref.getStringSharedPref
import org.koin.dsl.module

val dataModule = module {
    single {
        HttpClient(CIO) {
            // Установка базового URL
            defaultRequest {
                url("http://89.46.33.116:4040/api/") // базовый URL
            }

            // Установка тайм-аутов
            install(HttpTimeout) {
                requestTimeoutMillis = 45_000 // 45 секунд
                connectTimeoutMillis = 45_000 // 45 секунд
            }

            install(ContentNegotiation) {
                json(Json {
                    ignoreUnknownKeys = true
                    isLenient = true
                    coerceInputValues = true
                })
            }

            // Добавление пользовательских заголовков
            defaultRequest {
                val token = getStringSharedPref(URL.TOKEN.key)
                println("token: $token")

                header(HttpHeaders.Accept, ContentType.Application.Json)
                header(HttpHeaders.ContentType, ContentType.Application.Json)
                header(HttpHeaders.Authorization, "Bearer $token")
            }

            // Логгирование запросов и ответов для отладки
            install(Logging) {
                level = LogLevel.BODY
            }
        }
    }

    single<Repository> {
        RepositoryImpl(
            get(),
            get(),
            get(),
            get(),
            get()
        )
    }


    single<CatalogDataSource> {
        CatalogDataSourceImpl(get())
    }

    single<ProductActionsDataSource> {
        ProductActionsDataSourceImpl(get())
    }

    single<ProductsDataSource> {
        ProductsDataSourceImpl(get())
    }

    single<ProfileActionsDataSource> {
        ProfileActionsDataSourceImpl(get())
    }

    single<AuthDataSource> {
        AuthDataSourceImpl(get())
    }
}


object NavigationStateHolder {
    val navigationState = MutableStateFlow<NavigationState>(NavigationState.None)
}

sealed class NavigationState {
    data object None : NavigationState()
    data object Normal : NavigationState()
    data object TokenExpired : NavigationState()
    data class Error(val message: String) : NavigationState()
}


@Serializable
data class ErrorResponse(
    val status: String = "",
    @SerialName("message")
    val message: String? = null,
    val error: String = "",
    val statusCode: Int = 0
)

@Serializable
data class ErrorResponseListM(
    val status: String = "",
    @SerialName("message")
    val message: List<String> = listOf(),
    val error: String = "",
    val statusCode: Int = 0
)