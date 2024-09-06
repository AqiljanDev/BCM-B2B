package kz.bcm.b2b.data.repository

import kz.bcm.b2b.data.datasource.CatalogDataSourceImpl
import kz.bcm.b2b.domain.data.findOne.Catalog
import kz.bcm.b2b.domain.repository.Repository

class RepositoryImpl(
    private val catalogDataSource: CatalogDataSourceImpl
) : Repository {
    override suspend fun findOne(
        category: String,
        min: Int?,
        max: Int?,
        sort: String,
        f: String,
        page: Int
    ): Catalog {
        return catalogDataSource.findOne(category, min, max, sort, f, page)
    }
}