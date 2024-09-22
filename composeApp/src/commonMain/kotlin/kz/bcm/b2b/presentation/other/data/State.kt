package kz.bcm.b2b.presentation.other.data

import org.koin.core.logger.MESSAGE

sealed class State<out T> {
    data class Success< out T>(val data: T) : State<T>()
    object Loading : State<Nothing>()
    data class Error(val message: String) : State<Nothing>()
}