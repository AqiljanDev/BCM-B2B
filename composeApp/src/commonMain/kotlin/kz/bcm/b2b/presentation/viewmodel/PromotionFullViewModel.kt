package kz.bcm.b2b.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.bcm.b2b.data.dto.sale.GetSaleOneDto
import kz.bcm.b2b.domain.data.sale.GetSaleOne
import kz.bcm.b2b.domain.usecase.GetSaleOneUseCase

class PromotionFullViewModel(
    private val getSaleOneUseCase: GetSaleOneUseCase
) : ViewModel() {
    private val _sale = MutableStateFlow<GetSaleOne>(GetSaleOneDto())
    val sale get() = _sale.asStateFlow()


    fun getSale(slug: String) {
        viewModelScope.launch {
            val res = getSaleOneUseCase.execute(slug)

            _sale.emit(res)
        }
    }
}