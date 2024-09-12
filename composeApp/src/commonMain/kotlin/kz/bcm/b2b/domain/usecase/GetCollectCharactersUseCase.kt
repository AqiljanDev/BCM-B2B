package kz.bcm.b2b.domain.usecase

import kz.bcm.b2b.domain.data.collectCharacters.CollectCharacters
import kz.bcm.b2b.domain.repository.Repository

class GetCollectCharactersUseCase(
    private val repository: Repository
) {

    suspend fun execute(
        category: String,
        min: Int?,
        f: String
    ): CollectCharacters {
        return repository.collectCharacters(category, min, f)
    }
}