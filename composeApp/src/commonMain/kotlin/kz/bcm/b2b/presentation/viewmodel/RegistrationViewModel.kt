package kz.bcm.b2b.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.bcm.b2b.domain.data.auth.register.PostRegistration
import kz.bcm.b2b.domain.usecase.PostRegistrationUseCase
import kz.bcm.b2b.presentation.other.data.State

class RegistrationViewModel(
    private val postRegistrationUseCase: PostRegistrationUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<State<String>>(State.Loading)
    val state get() = _state.asStateFlow()


    fun registration(postRegistration: PostRegistration) {
        viewModelScope.launch {
            try {
                _state.emit(State.Loading)

                val res = postRegistrationUseCase.execute(postRegistration)

                _state.emit(State.Success(res.accessToken))
            } catch (e: Exception) {
                _state.emit(State.Error(e.message ?: "Unknown error"))
            }
        }
    }
}