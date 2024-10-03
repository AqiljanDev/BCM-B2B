package kz.bcm.b2b.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.bcm.b2b.domain.data.sale.GetSaleAll
import kz.bcm.b2b.domain.usecase.GetSaleUseCase

class PromotionViewModel(
    private val getSaleUseCase: GetSaleUseCase
) : ViewModel() {
    private val _sale = MutableStateFlow<List<GetSaleAll>>(listOf())
    val sale get() = _sale.asStateFlow()

    suspend fun getSale() {
        viewModelScope.launch {
            val res = getSaleUseCase.execute()

            _sale.emit(res)
        }
    }
}