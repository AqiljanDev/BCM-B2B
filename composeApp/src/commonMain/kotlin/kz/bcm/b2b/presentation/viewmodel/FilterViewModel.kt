package kz.bcm.b2b.presentation.viewmodel

import androidx.compose.runtime.MutableState
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.compose.viewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.bcm.b2b.data.dto.collectCharacters.CollectCharactersDto
import kz.bcm.b2b.data.dto.findOneCatalog.CatalogDto
import kz.bcm.b2b.domain.data.collectCharacters.CollectCharacters
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

class FilterViewModel(
    private val getFindOneUseCase: GetFindOneUseCase,
    private val getCollectCharactersUseCase: GetCollectCharactersUseCase,
    getCompareMiniUseCase: GetCompareMiniUseCase,
    getFavoriteMiniUseCase: GetFavoriteMiniUseCase,
    getCartMiniUseCase: GetCartMiniUseCase,
    eventCompareUseCase: EventCompareUseCase,
    eventFavoriteUseCase: EventFavoriteUseCase,
    eventCartUseCase: EventCartUseCase,
    deleteCartUseCase: DeleteCartUseCase

): ProductViewModel(
    getCompareMiniUseCase,
    getFavoriteMiniUseCase,
    getCartMiniUseCase,
    eventCompareUseCase,
    eventFavoriteUseCase,
    eventCartUseCase,
    deleteCartUseCase
) {

    private val _product = MutableStateFlow<Catalog>(CatalogDto())
    val product get() = _product.asStateFlow()

    private val _character = MutableStateFlow<CollectCharacters>(CollectCharactersDto())
    val character get() = _character.asStateFlow()


    fun getProduct(
        category: String,
        min: Int? = null,
        max: Int? = null,
        sort: String = "",
        f: String = "",
        page: Int
    ) {
        viewModelScope.launch {
            val res = getFindOneUseCase.execute(category, min, max, sort, f, page)

            _product.emit(res)
        }
    }

    fun getCharacters(
        category: String,
        min: Int? = null,
        f: String = ""
    ) {
        viewModelScope.launch {
            val res = getCollectCharactersUseCase.execute(category, min, f)
            println("get coll character = $res")

            _character.emit(res)
        }
    }
}















