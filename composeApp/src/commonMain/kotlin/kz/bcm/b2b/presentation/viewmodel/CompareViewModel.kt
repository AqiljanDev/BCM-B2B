package kz.bcm.b2b.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.bcm.b2b.data.dto.cart.mini.GetCartMiniDto
import kz.bcm.b2b.data.dto.compare.CompareFullDto
import kz.bcm.b2b.domain.data.cart.event.PostCart
import kz.bcm.b2b.domain.data.cart.mini.GetCartMini
import kz.bcm.b2b.domain.data.compare.CompareFull
import kz.bcm.b2b.domain.data.findOneCatalog.UserDiscount
import kz.bcm.b2b.domain.usecase.DeleteCartUseCase
import kz.bcm.b2b.domain.usecase.EventCartUseCase
import kz.bcm.b2b.domain.usecase.EventCompareUseCase
import kz.bcm.b2b.domain.usecase.GetCartMiniUseCase
import kz.bcm.b2b.domain.usecase.GetCompareFullUseCase
import kz.bcm.b2b.domain.usecase.GetUserDiscountUseCase

class CompareViewModel(
    private val getCompareFullUseCase: GetCompareFullUseCase,
    private val getCartMiniUseCase: GetCartMiniUseCase,
    private val eventCartUseCase: EventCartUseCase,
    private val eventCompareUseCase: EventCompareUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val getUserDiscountUseCase: GetUserDiscountUseCase
) : ViewModel() {

    private val _product = MutableStateFlow<CompareFull>(CompareFullDto())
    val product get() = _product.asStateFlow()

    private val _cart: MutableStateFlow<GetCartMini> = MutableStateFlow(GetCartMiniDto())
    val cart get() = _cart.asStateFlow()

    private val _userDiscount: MutableStateFlow<List<UserDiscount>> = MutableStateFlow(listOf())
    val userDiscount get() = _userDiscount.asStateFlow()


    fun getProduct() {
        viewModelScope.launch {
            val res = getCompareFullUseCase.execute()

            _product.emit(res)
        }
    }

    fun getCart() {
        viewModelScope.launch {
            val res = getCartMiniUseCase.execute()

            _cart.emit(res)
        }
    }

    fun updateCart(item: PostCart) {
        viewModelScope.launch {
            val res = eventCartUseCase.execute(item)

            _cart.emit(res)
        }
    }

    fun deleteCart(id: Int) {
        viewModelScope.launch {
            val res = deleteCartUseCase.execute(id)

            _cart.emit(res)
        }
    }

    fun deleteCompare(prodId: String) {
        viewModelScope.launch {
            eventCompareUseCase.execute(prodId)
            getProduct()
        }
    }

    fun getDiscount() {
        viewModelScope.launch {
            val res = getUserDiscountUseCase.execute()

            _userDiscount.emit(res)
        }
    }
}