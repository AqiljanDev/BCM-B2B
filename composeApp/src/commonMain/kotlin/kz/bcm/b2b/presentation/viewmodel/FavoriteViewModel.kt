package kz.bcm.b2b.presentation.viewmodel

import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.bcm.b2b.domain.data.wishlistAndCompare.wishlist.WishListFull
import kz.bcm.b2b.domain.usecase.DeleteCartUseCase
import kz.bcm.b2b.domain.usecase.EventCartUseCase
import kz.bcm.b2b.domain.usecase.EventCompareUseCase
import kz.bcm.b2b.domain.usecase.EventFavoriteUseCase
import kz.bcm.b2b.domain.usecase.GetCartMiniUseCase
import kz.bcm.b2b.domain.usecase.GetCompareMiniUseCase
import kz.bcm.b2b.domain.usecase.GetFavoriteFullUseCase
import kz.bcm.b2b.domain.usecase.GetFavoriteMiniUseCase
import kz.bcm.b2b.domain.usecase.GetUserDiscountUseCase

class FavoriteViewModel(
    private val getFavoriteFullUseCase: GetFavoriteFullUseCase,
    getCompareMiniUseCase: GetCompareMiniUseCase,
    getFavoriteMiniUseCase: GetFavoriteMiniUseCase,
    getCartMiniUseCase: GetCartMiniUseCase,
    eventCompareUseCase: EventCompareUseCase,
    private val eventFavoriteUseCase: EventFavoriteUseCase,
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

    private val _productsFeatured = MutableStateFlow<List<WishListFull>>(listOf())
    val productsFeatured get() = _productsFeatured.asStateFlow()


    fun getProducts() {
        viewModelScope.launch {
            val res = getFavoriteFullUseCase.execute()
            println("Favorite Screen = product: ${res.size}")

            _productsFeatured.emit(res)
        }
    }


    override fun eventFavorite(prodId: String) {

        viewModelScope.launch {
            eventFavoriteUseCase.execute(prodId)
            getProducts()
        }
    }
}