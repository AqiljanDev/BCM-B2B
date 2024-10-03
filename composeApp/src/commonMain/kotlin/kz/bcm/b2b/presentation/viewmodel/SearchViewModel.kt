package kz.bcm.b2b.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.bcm.b2b.domain.data.findOneCatalog.Product
import kz.bcm.b2b.domain.usecase.DeleteCartUseCase
import kz.bcm.b2b.domain.usecase.EventCartUseCase
import kz.bcm.b2b.domain.usecase.EventCompareUseCase
import kz.bcm.b2b.domain.usecase.EventFavoriteUseCase
import kz.bcm.b2b.domain.usecase.GetCartMiniUseCase
import kz.bcm.b2b.domain.usecase.GetCollectCharactersUseCase
import kz.bcm.b2b.domain.usecase.GetCompareMiniUseCase
import kz.bcm.b2b.domain.usecase.GetFavoriteMiniUseCase
import kz.bcm.b2b.domain.usecase.GetSearchUseCase
import kz.bcm.b2b.domain.usecase.GetUserDiscountUseCase

class SearchViewModel(
    private val getSearchUseCase: GetSearchUseCase,
    private val getCollectCharactersUseCase: GetCollectCharactersUseCase,
    getCompareMiniUseCase: GetCompareMiniUseCase,
    getFavoriteMiniUseCase: GetFavoriteMiniUseCase,
    getCartMiniUseCase: GetCartMiniUseCase,
    eventCompareUseCase: EventCompareUseCase,
    eventFavoriteUseCase: EventFavoriteUseCase,
    eventCartUseCase: EventCartUseCase,
    deleteCartUseCase: DeleteCartUseCase,
    getUserDiscountUseCase: GetUserDiscountUseCase
) : ProductViewModel(
    getCompareMiniUseCase,
    getFavoriteMiniUseCase,
    getCartMiniUseCase,
    eventCompareUseCase,
    eventFavoriteUseCase,
    eventCartUseCase,
    deleteCartUseCase,
    getUserDiscountUseCase
) {
    private val _product = MutableStateFlow<List<Product>>(listOf())
    val product get() = _product.asStateFlow()

    fun getProduct(value: String) {
        viewModelScope.launch {
            val res = getSearchUseCase.execute(value)
            _product.emit(res)
        }
    }

}