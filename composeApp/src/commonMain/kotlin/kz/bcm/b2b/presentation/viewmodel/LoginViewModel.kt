package kz.bcm.b2b.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.bcm.b2b.domain.data.auth.login.PostLogin
import kz.bcm.b2b.domain.data.bill.BillBody
import kz.bcm.b2b.domain.usecase.PostLoginUseCase
import kz.bcm.b2b.presentation.other.data.State

class LoginViewModel(
    private val postLoginUseCase: PostLoginUseCase
) : ViewModel() {

    private val _state = MutableStateFlow<State<String>?>(State.Loading)
    val state get() = _state.asStateFlow()


    fun postLogin(body: PostLogin) {
        viewModelScope.launch {
            try {
                _state.value = State.Loading
                val res = postLoginUseCase.execute(body)
                println("token = ${res.accessToken}")

                _state.value = State.Success(res.accessToken)
            } catch (e: Exception) {
                println("Exception =-=-= ${e.message}")

                _state.value = State.Error(e.message.toString())
            }
        }
    }

    fun resetState() {
        viewModelScope.launch {
            _state.emit(State.Loading)
        }
    }
}