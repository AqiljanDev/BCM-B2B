package kz.bcm.b2b.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.bcm.b2b.data.dto.collectCharacters.CollectCharactersDto
import kz.bcm.b2b.domain.data.collectCharacters.CollectCharacters
import kz.bcm.b2b.domain.usecase.GetCollectCharactersUseCase
import kz.bcm.b2b.domain.usecase.GetFindOneUseCase

class FilterFullViewModel(
    private val getCollectCharactersUseCase: GetCollectCharactersUseCase,
    private val getFindOneUseCase: GetFindOneUseCase
) : ViewModel() {

    private val _character = MutableStateFlow<CollectCharacters>(CollectCharactersDto())
    val character get() = _character.asStateFlow()

    private val _title = MutableStateFlow<String>("Каталог")
    val title get() = _title.asStateFlow()

    fun getTitle(category: String) {
        viewModelScope.launch {
            val res = getFindOneUseCase.execute(
                category = category,
                min = null,
                max = null,
                sort = "new",
                f = "",
                page = 1
            )

            _title.emit(res.info.title)
        }
    }

    fun getCharacter(
        category: String,
        min: Int? = null,
        f: String = ""
    ) {
        viewModelScope.launch {
            val res = getCollectCharactersUseCase.execute(category, min, f)

            _character.emit(res)
        }
    }
}