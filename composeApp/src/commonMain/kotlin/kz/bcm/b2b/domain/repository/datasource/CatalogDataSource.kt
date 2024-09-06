package kz.bcm.b2b.domain.repository.datasource

import kz.bcm.b2b.domain.data.findOne.Catalog

interface CatalogDataSource {

    suspend fun findOne(
        category: String,
        min: Int?,
        max: Int?,
        sort: String,
        f: String,
        page: Int
    ): Catalog
}