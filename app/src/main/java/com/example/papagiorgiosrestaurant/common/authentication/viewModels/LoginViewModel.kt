package com.example.papagiorgiosrestaurant.common.authentication.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.papagiorgiosrestaurant.common.api.repository.LoginRepository
import com.example.papagiorgiosrestaurant.common.models.LoginRequest

class LoginViewModel: ViewModel() {

    private val _success = MutableLiveData<String>()
    val success: LiveData<String> get() = _success

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> get() = _error

    fun loginUser(loginRequest: LoginRequest) {
        LoginRepository.loginUser(
            loginRequest,
            onSuccess = {
                print("LoginVM: ${loginRequest.email} ${loginRequest.password}")
                _success.postValue("Usuario logueado")
            },
            onError = {
                _error.postValue(it.message ?: "Error desconocido")
            }
        )
    }


}