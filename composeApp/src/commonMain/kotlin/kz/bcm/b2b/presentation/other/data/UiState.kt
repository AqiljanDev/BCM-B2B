package kz.bcm.b2b.presentation.other.data

// Определяем UiState как sealed-класс
sealed class UiState<out T> {
    object Loading : UiState<Nothing>()

    data class Error(val exception: String) : UiState<Nothing>()

    data class Success<out T>(val data: T) : UiState<T>()
}
