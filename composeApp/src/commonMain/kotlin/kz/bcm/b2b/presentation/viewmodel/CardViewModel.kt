package kz.bcm.b2b.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.bcm.b2b.data.dto.findOneProduct.FindOneProductDto
import kz.bcm.b2b.domain.data.findOneProduct.FindOneProduct
import kz.bcm.b2b.domain.usecase.DeleteCartUseCase
import kz.bcm.b2b.domain.usecase.EventCartUseCase
import kz.bcm.b2b.domain.usecase.EventCompareUseCase
import kz.bcm.b2b.domain.usecase.EventFavoriteUseCase
import kz.bcm.b2b.domain.usecase.GetCartMiniUseCase
import kz.bcm.b2b.domain.usecase.GetCompareMiniUseCase
import kz.bcm.b2b.domain.usecase.GetFavoriteMiniUseCase
import kz.bcm.b2b.domain.usecase.GetFindOneProductsUseCase
import kz.bcm.b2b.domain.usecase.GetUserDiscountUseCase

class CardViewModel(
    private val getFindOneProductsUseCase: GetFindOneProductsUseCase,
    getUserDiscountUseCase: GetUserDiscountUseCase,
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
    deleteCartUseCase,
    getUserDiscountUseCase
) {
    private val _product = MutableStateFlow<FindOneProduct>(FindOneProductDto())
    val product get() = _product.asStateFlow()


    fun getFindOneProduct(slug: String) {
        println("CardViewModel -> getFindOneProduct = slug: $slug")

        viewModelScope.launch {
            val res = getFindOneProductsUseCase.execute(slug)

            _product.emit(res)
        }
    }
}

