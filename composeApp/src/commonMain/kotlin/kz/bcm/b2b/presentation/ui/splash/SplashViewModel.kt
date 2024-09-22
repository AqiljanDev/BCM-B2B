package kz.bcm.b2b.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.request.get
import kotlinx.coroutines.launch
import kz.bcm.b2b.di.NavigationState
import kz.bcm.b2b.di.NavigationStateHolder

class SplashViewModel(
    private val httpClient: HttpClient
): ViewModel() {

    init {
        checkToken()
    }

    fun checkToken() {
        viewModelScope.launch {
            try {
                println("check token")
                httpClient.get("auth/check")

                NavigationStateHolder.navigationState.emit(NavigationState.Normal)
            }catch (e: Exception) {

                println("Ex message: ${e.message}")

                NavigationStateHolder.navigationState.emit(NavigationState.TokenExpired)
            }
        }
    }
}