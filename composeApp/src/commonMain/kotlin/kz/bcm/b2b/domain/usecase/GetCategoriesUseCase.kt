package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.categories.ChildCategory
import kz.bcm.b2b.domain.repository.Repository

class GetCategoriesUseCase(
    private val repository: Repository
) {

    suspend fun execute(): List<ChildCategory> {
        return repository.getCategories()
    }
}
