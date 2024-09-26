package kz.bcm.b2b.presentation.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kz.bcm.b2b.data.dto.auth.passwordCodeSend.PasswordSendDto
import kz.bcm.b2b.domain.usecase.PostPasswordCodeSendUseCase
import kz.bcm.b2b.presentation.other.data.State

class RestoreCodeViewModel(
    private val postPasswordCodeSendUseCase: PostPasswordCodeSendUseCase
) : ViewModel() {
    private val _state = MutableStateFlow<State<String>>(State.Loading)
    val state get() = _state.asStateFlow()


    fun codeSend(email: String) {
        val passwordSend = PasswordSendDto(email = email)

        viewModelScope.launch {

            try {
                postPasswordCodeSendUseCase.execute(passwordSend)
                _state.emit(State.Success(""))
            } catch (e: Exception) {

                _state.emit(State.Error(message = e.message ?: "Пользователь с такой почтой не найден!"))
            }
        }
    }
}