package kz.bcm.b2b.domain.repository.datasource

import kz.bcm.b2b.domain.data.findOneProduct.FindOneProduct

interface ProductsDataSource {

    suspend fun findOne(slug: String): FindOneProduct
}
