package kz.bcm.b2b.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.bcm.b2b.data.dto.cart.full.CartFullProductDto
import kz.bcm.b2b.data.dto.cart.mini.GetCartMiniDto
import kz.bcm.b2b.data.dto.cart.mini.ProductDto
import kz.bcm.b2b.domain.data.bill.BillMy
import kz.bcm.b2b.domain.data.cart.event.PostCart
import kz.bcm.b2b.domain.data.cart.full.CartFullProduct
import kz.bcm.b2b.domain.data.cart.mini.GetCartMini
import kz.bcm.b2b.domain.data.findOneCatalog.Product
import kz.bcm.b2b.domain.data.orders.PostOrders
import kz.bcm.b2b.domain.usecase.DeleteCartUseCase
import kz.bcm.b2b.domain.usecase.EventCartUseCase
import kz.bcm.b2b.domain.usecase.GetBillMyUseCase
import kz.bcm.b2b.domain.usecase.GetCartFullUseCase
import kz.bcm.b2b.domain.usecase.PostOrdersUseCase

class CartViewModel(
    private val getCartFullUseCase: GetCartFullUseCase,
    private val eventCartUseCase: EventCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val getBillMyUseCase: GetBillMyUseCase,
    private val postOrdersUseCase: PostOrdersUseCase
) : ViewModel() {

    private val _cartProduct = MutableStateFlow<List<CartFullProduct>>(listOf())
    val cartProduct get() = _cartProduct.asStateFlow()

    private val _cartMini = MutableStateFlow<GetCartMini>(GetCartMiniDto())
    val cartMini get() = _cartMini.asStateFlow()

    private val _billMy: MutableStateFlow<List<BillMy>> = MutableStateFlow(listOf())
    val billMy get() = _billMy.asStateFlow()

    fun getCartFull() {
        viewModelScope.launch {
            val res = getCartFullUseCase.execute()

            val mini = GetCartMiniDto(
                products = res.products
                    .map {
                        ProductDto(
                            id = it.id,
                            userId = it.userId,
                            prodId = it.prodId,
                            count = it.count
                        )
                    },
                totalCount = res.totalCount
            )

            _cartMini.emit(mini)
            _cartProduct.emit(res.products)
        }
    }

    fun eventCart(item: PostCart) {
        viewModelScope.launch {
            val res = eventCartUseCase.execute(item)

            _cartMini.emit(res)
        }
    }

    fun deleteCart(id: Int) {
        viewModelScope.launch {
            val res = deleteCartUseCase.execute(id)

            println("res = $res")
            _cartMini.emit(res)
            getCartFull()
        }
    }

    fun getBillMy() {
        viewModelScope.launch {
            val res = getBillMyUseCase.execute()

            _billMy.emit(res)
        }
    }

    fun postOrders(postOrder: PostOrders) {
        viewModelScope.launch {
            println("post orders = $postOrder")

            postOrdersUseCase.execute(postOrder)

            getCartFull()
            getBillMy()
        }
    }
}





