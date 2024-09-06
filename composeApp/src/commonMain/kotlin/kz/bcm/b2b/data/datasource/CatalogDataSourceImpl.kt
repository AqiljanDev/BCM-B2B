package kz.bcm.b2b.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kz.bcm.b2b.data.dto.findOne.CatalogDto
import kz.bcm.b2b.domain.data.findOne.Catalog
import kz.bcm.b2b.domain.repository.datasource.CatalogDataSource

class CatalogDataSourceImpl(
//    private val httpClient: HttpClient
) : CatalogDataSource {


    override suspend fun findOne(
        category: String,
        min: Int?,
        max: Int?,
        sort: String,
        f: String,
        page: Int
    ): Catalog {

        val url = buildString {
            append("catalog/$category?page=$page")

            min?.let { append("&min=$it") }
            max?.let { append("&max=$it") }
            if (sort.isNotEmpty()) append("&sort=$sort")
            if (f.isNotEmpty()) append("&f=$f")
        }

        val response: CatalogDto = CatalogDto()

        return response
    }

}