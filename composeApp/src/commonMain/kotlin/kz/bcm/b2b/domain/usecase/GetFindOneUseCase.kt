package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.data.repository.RepositoryImpl
import kz.bcm.b2b.domain.data.findOne.Catalog
import kz.bcm.b2b.domain.repository.Repository

class GetFindOneUseCase(
    private val repository: Repository
) {

    suspend fun execute(
        category: String,
        min: Int?,
        max: Int?,
        sort: String,
        f: String,
        page: Int
    ): Catalog {
        return repository.findOne(
            category, min, max, sort, f, page
        )
    }
}
