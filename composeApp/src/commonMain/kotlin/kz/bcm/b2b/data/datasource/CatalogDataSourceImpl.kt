package kz.bcm.b2b.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kz.bcm.b2b.data.dto.collectCharacters.CollectCharactersDto
import kz.bcm.b2b.data.dto.findOneCatalog.CatalogDto
import kz.bcm.b2b.domain.data.collectCharacters.CollectCharacters
import kz.bcm.b2b.domain.data.findOneCatalog.Catalog
import kz.bcm.b2b.domain.repository.datasource.CatalogDataSource

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

        val url = buildString {
            append("catalog/$category?page=$page")

            min?.let { append("&min=$it") }
            max?.let { append("&max=$it") }
            if (sort.isNotEmpty()) append("&sort=$sort")
            if (f.isNotEmpty()) append("&f=$f")
        }

        val response: CatalogDto = httpClient.get(url).body()

        return response
    }

    override suspend fun collectCharacters(
        category: String,
        min: Int?,
        f: String
    ): CollectCharacters {

        val url = buildString {
            append("catalog/char/$category")

            var isFirstParam = true // Флаг для отслеживания первого параметра

            min?.let {
                append(if (isFirstParam) "?" else "&")
                append("min=$min")
                isFirstParam = false
            }

            if (f.isNotEmpty()) {
                append(if (isFirstParam) "?" else "&")
                append("f=$f")
            }
        }

        val response: CollectCharactersDto = httpClient.get(url).body()

        return response
    }


}












