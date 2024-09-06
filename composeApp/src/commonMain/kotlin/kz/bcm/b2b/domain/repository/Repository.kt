package kz.bcm.b2b.domain.repository

import kz.bcm.b2b.domain.data.findOne.Catalog

interface Repository {

    suspend fun findOne(
        category: String,
        min: Int?,
        max: Int?,
        sort: String,
        f: String,
        page: Int
    ): Catalog
}