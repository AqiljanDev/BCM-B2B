package kz.bcm.b2b.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kz.bcm.b2b.data.dto.bill.BillMyDto
import kz.bcm.b2b.data.dto.cabinet.CabinetDto
import kz.bcm.b2b.data.dto.cart.mini.GetCartMiniDto
import kz.bcm.b2b.data.dto.order.findOne.FindOneProductDto
import kz.bcm.b2b.domain.data.bill.BillBody
import kz.bcm.b2b.domain.data.bill.BillMy
import kz.bcm.b2b.domain.data.cabinet.Cabinet
import kz.bcm.b2b.domain.data.cart.event.PostCart
import kz.bcm.b2b.domain.data.cart.mini.GetCartMini
import kz.bcm.b2b.domain.data.order.findMyOrder.MyOrder
import kz.bcm.b2b.domain.data.findOneCatalog.UserDiscount
import kz.bcm.b2b.domain.data.order.findOne.FindOneProduct
import kz.bcm.b2b.domain.usecase.DeleteBillUseCase
import kz.bcm.b2b.domain.usecase.DeleteCartUseCase
import kz.bcm.b2b.domain.usecase.EventCartUseCase
import kz.bcm.b2b.domain.usecase.EventCompareUseCase
import kz.bcm.b2b.domain.usecase.EventFavoriteUseCase
import kz.bcm.b2b.domain.usecase.GetBillMyUseCase
import kz.bcm.b2b.domain.usecase.GetCabinetUseCase
import kz.bcm.b2b.domain.usecase.GetCartMiniUseCase
import kz.bcm.b2b.domain.usecase.GetFindOneOrderUseCase
import kz.bcm.b2b.domain.usecase.GetMyOrdersUseCase
import kz.bcm.b2b.domain.usecase.GetUserDiscountUseCase
import kz.bcm.b2b.domain.usecase.PostBillUseCase
import kz.bcm.b2b.domain.usecase.PutBillUseCase
import kz.bcm.b2b.domain.usecase.PutCabinetUseCase

class ProfileViewModel(
    private val getMyOrdersUseCase: GetMyOrdersUseCase,
    private val getUserDiscountUseCase: GetUserDiscountUseCase,
    private val getFindOneOrderUseCase: GetFindOneOrderUseCase,
    private val getCartMiniUseCase: GetCartMiniUseCase,
    private val eventCartUseCase: EventCartUseCase,
    private val deleteCartUseCase: DeleteCartUseCase,
    private val getCabinetUseCase: GetCabinetUseCase,
    private val putCabinetUseCase: PutCabinetUseCase,
    private val getBillMyUseCase: GetBillMyUseCase,
    private val postBillUseCase: PostBillUseCase,
    private val putBillUseCase: PutBillUseCase,
    private val deleteBillUseCase: DeleteBillUseCase
) : ViewModel() {

    private val _order = MutableStateFlow<List<MyOrder>>(listOf())
    val order get() = _order.asStateFlow()

    private val _userDiscount = MutableStateFlow<List<UserDiscount>>(listOf())
    val userDiscount get() = _userDiscount.asStateFlow()

    private val _orderFindOne = MutableStateFlow<FindOneProduct>(FindOneProductDto())
    val orderFindOne get() = _orderFindOne.asStateFlow()

    private val _cart = MutableStateFlow<GetCartMini>(GetCartMiniDto())
    val cart get() = _cart.asStateFlow()

    private val _cabinet = MutableStateFlow<Cabinet>(CabinetDto())
    val cabinet get() = _cabinet.asStateFlow()

    private val _bills = MutableStateFlow<List<BillMy>>(listOf())
    val bills get() = _bills.asStateFlow()


    fun getOrder() {
        viewModelScope.launch {
            val res = getMyOrdersUseCase.execute()

            _order.emit(res)
        }
    }

    fun getUserDiscount() {
        viewModelScope.launch {
            val res = getUserDiscountUseCase.execute()

            _userDiscount.emit(res)
        }
    }

    fun getFindOneOrder(id: Int) {
        viewModelScope.launch {
            val res = getFindOneOrderUseCase.execute(id)

            println("ProfileScreen: getFindOneOrderL id: $id, res = $res")

            _orderFindOne.emit(res)
        }
    }


    fun getMiniCart() {
        viewModelScope.launch {
            val res = getCartMiniUseCase.execute()
            println("getMiniCart - ProductViewModel")

            _cart.emit(res)
        }
    }


    fun eventCart(item: PostCart) {
        viewModelScope.launch {
            println("ProfileScreen: eventCart = $item")

            _cart.emit(eventCartUseCase.execute(item))
        }
    }

    fun deleteCart(id: Int) {
        viewModelScope.launch {
            _cart.emit(deleteCartUseCase.execute(id))
        }
    }


    fun getCabinet() {
        viewModelScope.launch {
            val res = getCabinetUseCase.execute()

            _cabinet.emit(res)
        }
    }


    fun updateCabinet(cabinet: Cabinet) {
        viewModelScope.launch {
            val res = putCabinetUseCase.execute(cabinet)

            _cabinet.emit(res)
        }
    }




    fun getBills() {
        viewModelScope.launch {
            val res = getBillMyUseCase.execute()

            _bills.emit(res)
        }
    }

    fun createBills(body: BillBody) {
        viewModelScope.launch {
            val res = postBillUseCase.execute(body)

            _bills.update { currBills ->
                currBills + res
            }
        }
    }

    fun updateBills(id: Int, body: BillBody) {
        viewModelScope.launch {
            val res = putBillUseCase.execute(id, body)

            _bills.update { currBills ->

                currBills.map { bill ->
                    if (bill.id == res.id) {
                        res
                    } else
                        bill
                }
            }
        }
    }

    fun deleteBills(id: Int) {
        viewModelScope.launch {
            deleteBillUseCase.execute(id)

            _bills.update { currBills ->
                currBills.filter {
                    it.id != id
                }
            }
        }
    }
}