package kz.bcm.b2b.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpMethod
import io.ktor.http.HttpStatusCode
import io.ktor.http.appendPathSegments
import kotlinx.serialization.SerializationException
import kz.bcm.b2b.data.dto.categories.ChildCategoryDto
import kz.bcm.b2b.data.dto.collectCharacters.CollectCharactersDto
import kz.bcm.b2b.data.dto.findOneCatalog.CatalogDto
import kz.bcm.b2b.data.dto.findOneCatalog.ProductDto
import kz.bcm.b2b.di.ErrorResponse
import kz.bcm.b2b.domain.data.collectCharacters.CollectCharacters
import kz.bcm.b2b.domain.data.findOneCatalog.Catalog
import kz.bcm.b2b.domain.data.findOneCatalog.ChildCategory
import kz.bcm.b2b.domain.data.findOneCatalog.Product
import kz.bcm.b2b.domain.repository.datasource.CatalogDataSource
import kz.bcm.b2b.presentation.other.ApiResponse
import kz.bcm.b2b.presentation.other.safeRequest

class CatalogDataSourceImpl(
    private val httpClient: HttpClient
) : CatalogDataSource {


    override suspend fun findOne(
        category: String,
        min: Int?,
        max: Int?,
        sort: String,
        f: String,
        page: Int
    ): Catalog {
        println("CatalogSource -> findOne = category: $category, page: $page")

        val req = httpClient.safeRequest<CatalogDto, ErrorResponse> {
            method = HttpMethod.Get

            url {
                appendPathSegments("catalog/$category")
                parameters.apply {
                    append("page", page.toString())
                    min?.let { append("min", it.toString()) }
                    max?.let { append("max", it.toString()) }
                    if (sort.isNotEmpty()) append("sort", sort)
                    if (f.isNotEmpty()) append("f", f)
                }
            }
        }

        if (req is ApiResponse.Success) return req.body

        return CatalogDto()
    }

    override suspend fun collectCharacters(
        category: String,
        min: Int?,
        f: String
    ): CollectCharacters {
        println("collectCharacters = category: $category, min: $min, f: $f")

        val entry = httpClient.safeRequest<CollectCharactersDto, ErrorResponse> {
            method = HttpMethod.Get

            url {
                appendPathSegments("catalog/char/$category")

                parameters.apply {
                    min?.let { append("min", it.toString()) }
                    if (f.isNotEmpty()) append("f", f)
                }
            }
        }

        println("collectCharacters entry = ${entry is ApiResponse.Success}")

        return if (entry is ApiResponse.Success) {

            println("Entry = ${entry.body}")

            entry.body
        } else {
            println("Entry = else -- if is else")
            CollectCharactersDto()
        }
    }


    override suspend fun search(value: String): List<Product> {
        val body = httpClient.safeRequest<List<ProductDto>, ErrorResponse> {
            method = HttpMethod.Get

            url {
                appendPathSegments("search")
                parameters.append("s", value)
            }
        }

        if (body is ApiResponse.Success) return body.body

        return listOf()
    }

    override suspend fun getCategories(): List<kz.bcm.b2b.domain.data.categories.ChildCategory> {
        val body =
            httpClient.safeRequest<List<ChildCategoryDto>, ErrorResponse> {
                method = HttpMethod.Get

                url {
                    appendPathSegments("categories")
                }
            }

        return if (body is ApiResponse.Success) body.body
        else emptyList()
    }


}












