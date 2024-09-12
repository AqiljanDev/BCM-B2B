package kz.bcm.b2b.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.bcm.b2b.data.dto.cart.mini.GetCartMiniDto
import kz.bcm.b2b.domain.data.cart.event.PostCart
import kz.bcm.b2b.domain.data.cart.mini.GetCartMini
import kz.bcm.b2b.domain.data.wishlistAndCompare.GetMini
import kz.bcm.b2b.domain.usecase.DeleteCartUseCase
import kz.bcm.b2b.domain.usecase.EventCartUseCase
import kz.bcm.b2b.domain.usecase.EventCompareUseCase
import kz.bcm.b2b.domain.usecase.EventFavoriteUseCase
import kz.bcm.b2b.domain.usecase.GetCartMiniUseCase
import kz.bcm.b2b.domain.usecase.GetCompareMiniUseCase
import kz.bcm.b2b.domain.usecase.GetFavoriteMiniUseCase

open class ProductViewModel(
    private val getCompareMiniUseCase: GetCompareMiniUseCase,
    private val getFavoriteMiniUseCase: GetFavoriteMiniUseCase,
    private val getCartMiniUseCase: GetCartMiniUseCase,
    private val eventCompareUseCase: EventCompareUseCase,
    private val eventFavoriteUseCase: EventFavoriteUseCase,
    private val eventCartUseCase: EventCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase
) : ViewModel() {

    private val _compare = MutableStateFlow<List<GetMini>>(listOf())
    val compare get() = _compare.asStateFlow()

    private val _favorite = MutableStateFlow<List<GetMini>>(listOf())
    val favorite get() = _favorite.asStateFlow()

    private val _cart = MutableStateFlow<GetCartMini>(GetCartMiniDto())
    val cart get() = _cart.asStateFlow()


    fun initializeData() {
        getMiniCompare()
        getMiniFavorite()
        getMiniCart()
    }


    private fun getMiniCompare() {
        viewModelScope.launch {
            val res = getCompareMiniUseCase.execute()

            _compare.emit(res)
        }
    }

    private fun getMiniFavorite() {
        viewModelScope.launch {
            val res = getFavoriteMiniUseCase.execute()

            _favorite.emit(res)
        }
    }

    private fun getMiniCart() {
        viewModelScope.launch {
            val res = getCartMiniUseCase.execute()

            _cart.emit(res)
        }
    }

    fun eventCompare(prodId: String) {
        viewModelScope.launch {
            val res = eventCompareUseCase.execute(prodId)

            _compare.emit(res)
        }
    }

    fun eventFavorite(prodId: String) {
        viewModelScope.launch {
            val res = eventFavoriteUseCase.execute(prodId)

            _favorite.emit(res)
        }
    }

    fun eventCart(item: PostCart, id: Int) {
        viewModelScope.launch {

            println("Event cart = $item, id = $id")

            _cart.emit(
                if (item.count > 0)
                    eventCartUseCase.execute(item)
                else
                    deleteCartUseCase.execute(id)
            )

        }
    }
}