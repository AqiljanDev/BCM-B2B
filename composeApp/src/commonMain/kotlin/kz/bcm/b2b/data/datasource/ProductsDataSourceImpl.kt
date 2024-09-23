package kz.bcm.b2b.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.http.HttpMethod
import io.ktor.http.appendPathSegments
import kz.bcm.b2b.data.dto.findOneProduct.FindOneProductDto
import kz.bcm.b2b.di.ErrorResponse
import kz.bcm.b2b.domain.data.findOneProduct.FindOneProduct
import kz.bcm.b2b.domain.repository.datasource.ProductsDataSource
import kz.bcm.b2b.presentation.other.ApiResponse
import kz.bcm.b2b.presentation.other.safeRequest

class ProductsDataSourceImpl(
    private val httpClient: HttpClient
) : ProductsDataSource {

    override suspend fun findOne(slug: String): FindOneProduct {
        println("ProductSource -> findOne = slug: $slug")

        val entry = httpClient.safeRequest<FindOneProductDto, ErrorResponse> {
            method = HttpMethod.Get

            url {
                appendPathSegments("products/$slug")
            }
        }

        return if (entry is ApiResponse.Success) entry.body else FindOneProductDto()
    }


}
