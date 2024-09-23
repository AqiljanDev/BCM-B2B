package kz.bcm.b2b.presentation.ui.splash

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.get
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import kotlinx.coroutines.launch
import kz.bcm.b2b.di.NavigationState
import kz.bcm.b2b.di.NavigationStateHolder

class SplashViewModel(
    private val httpClient: HttpClient
) : ViewModel() {

    init {
        checkToken()
    }

    fun checkToken() {
        viewModelScope.launch {
            try {
                println("check token")
                val response: HttpResponse = httpClient.get("auth/check")
                println("normal token = ${response.status} - ${HttpStatusCode.OK}")

                if (response.status == HttpStatusCode.OK) {

                    NavigationStateHolder.navigationState.emit(NavigationState.Normal)
                } else {

                    NavigationStateHolder.navigationState.emit(NavigationState.TokenExpired)
                }

            } catch (e: Exception) {

                println("Ex message: ${e.message}")

                NavigationStateHolder.navigationState.emit(NavigationState.TokenExpired)
            }
        }
    }
}