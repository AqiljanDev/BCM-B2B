package kz.bcm.b2b.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.bcm.b2b.data.dto.findOneCatalog.CatalogDto
import kz.bcm.b2b.domain.data.findOneCatalog.Catalog
import kz.bcm.b2b.domain.usecase.DeleteCartUseCase
import kz.bcm.b2b.domain.usecase.EventCartUseCase
import kz.bcm.b2b.domain.usecase.EventCompareUseCase
import kz.bcm.b2b.domain.usecase.EventFavoriteUseCase
import kz.bcm.b2b.domain.usecase.GetCartMiniUseCase
import kz.bcm.b2b.domain.usecase.GetCollectCharactersUseCase
import kz.bcm.b2b.domain.usecase.GetCompareMiniUseCase
import kz.bcm.b2b.domain.usecase.GetFavoriteMiniUseCase
import kz.bcm.b2b.domain.usecase.GetFindOneUseCase

class CatalogViewModel(
    private val getFindOneUseCase: GetFindOneUseCase,
    private val getCollectCharactersUseCase: GetCollectCharactersUseCase,
    getCompareMiniUseCase: GetCompareMiniUseCase,
    getFavoriteMiniUseCase: GetFavoriteMiniUseCase,
    getCartMiniUseCase: GetCartMiniUseCase,
    eventCompareUseCase: EventCompareUseCase,
    eventFavoriteUseCase: EventFavoriteUseCase,
    eventCartUseCase: EventCartUseCase,
    deleteCartUseCase: DeleteCartUseCase
) : ProductViewModel(
    getCompareMiniUseCase,
    getFavoriteMiniUseCase,
    getCartMiniUseCase,
    eventCompareUseCase,
    eventFavoriteUseCase,
    eventCartUseCase,
    deleteCartUseCase
) {

    private val _catalog = MutableStateFlow<Catalog>(CatalogDto())
    val catalog: StateFlow<Catalog> = _catalog.asStateFlow()

    private val _page = MutableStateFlow<Int>(0)
    val page get() = _page.asStateFlow()

    fun getFindOne(
        category: String,
        min: Int? = null,
        max: Int? = null,
        sort: String = "",
        f: String = "",
        page: Int
    ) {
        viewModelScope.launch {

            println("CatalogViewModel -> getFindOne = category: $category")

            val res = getFindOneUseCase.execute(category, min, max, sort, f, page)

            _catalog.emit(res)
        }
    }


    fun getPage(
        category: String,
        min: Int? = null,
        f: String = ""
    ) {
        viewModelScope.launch {
            try {
                val res = getCollectCharactersUseCase.execute(category, min, f)

                _page.emit(res.pages)
            }catch (e: Exception) {
                println("sss")
            }
        }
    }


}