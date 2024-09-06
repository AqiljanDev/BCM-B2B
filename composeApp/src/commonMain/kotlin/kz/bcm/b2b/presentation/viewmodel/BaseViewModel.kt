package kz.bcm.b2b.presentation.viewmodel

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kz.bcm.b2b.presentation.other.data.UiState

open class BaseViewModel : ViewModel() {

    suspend fun <T> fetchData(
        uiStateFlow: MutableStateFlow<UiState<T?>>,
        apiCall: suspend () -> T  // Функция, возвращающая T напрямую
    ) {
        uiStateFlow.value = UiState.Loading
        try {
            // Выполняем API вызов и оборачиваем результат в UiState.Success
            val result = apiCall()
            uiStateFlow.value = UiState.Success(result)
        } catch (e: Exception) {
            // Обрабатываем ошибки и оборачиваем их в UiState.Error
            uiStateFlow.value = UiState.Error(e.message ?: "An unknown error occurred")
        }
    }
}
