package kz.bcm.b2b.data.datasource

import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import kz.bcm.b2b.data.dto.findOneProduct.FindOneProductDto
import kz.bcm.b2b.domain.data.findOneProduct.FindOneProduct
import kz.bcm.b2b.domain.repository.datasource.ProductsDataSource

class ProductsDataSourceImpl(
    private val httpClient: HttpClient
) : ProductsDataSource {

    override suspend fun findOne(slug: String): FindOneProduct {
        println("ProductSource -> findOne = slug: $slug")

        val response: FindOneProductDto = httpClient.get("products/$slug").body()

        return response
    }


}
