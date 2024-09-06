package kz.bcm.b2b.presentation.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.bcm.b2b.domain.data.findOne.Catalog
import kz.bcm.b2b.domain.usecase.GetFindOneUseCase

class CatalogViewModel(
    private val getFindOneUseCase: GetFindOneUseCase
) {

    private val _catalog = MutableStateFlow<Catalog?>(null)
    val catalog: StateFlow<Catalog?> = _catalog.asStateFlow()

    private val viewModelScope = CoroutineScope(Dispatchers.Main)


    fun getFindOne(
        category: String,
        min: Int? = null,
        max: Int? = null,
        sort: String = "",
        f: String = "",
        page: Int
    ) {
        viewModelScope.launch {
            val res = getFindOneUseCase.execute(category, min, max, sort, f, page)

            _catalog.emit(res)
        }
    }

}