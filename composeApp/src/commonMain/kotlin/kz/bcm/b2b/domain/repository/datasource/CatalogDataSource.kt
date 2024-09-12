package kz.bcm.b2b.domain.repository.datasource

import kz.bcm.b2b.domain.data.collectCharacters.CollectCharacters
import kz.bcm.b2b.domain.data.findOneCatalog.Catalog

interface CatalogDataSource {

    suspend fun findOne(
        category: String,
        min: Int?,
        max: Int?,
        sort: String,
        f: String,
        page: Int
    ): Catalog

    suspend fun collectCharacters(
        category: String,
        min: Int?,
        f: String
    ): CollectCharacters
}