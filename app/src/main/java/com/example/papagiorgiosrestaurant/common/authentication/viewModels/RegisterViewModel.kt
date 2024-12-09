package com.example.papagiorgiosrestaurant.common.authentication.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.papagiorgiosrestaurant.common.api.repository.RegisterRepository
import com.example.papagiorgiosrestaurant.common.models.User
import com.example.papagiorgiosrestaurant.common.models.users

class RegisterViewModel: ViewModel() {

    private val _success = MutableLiveData<Boolean>()
    val success: LiveData<Boolean> = _success

    private val _error = MutableLiveData<String>()
    val error: LiveData<String> = _error

    fun registerUser(user: User) {
        RegisterRepository.registerUser(
            user,
            onSuccess = {
                println("RegisterVM: ${user.name} ${user.email} ${user.password} ")
                _success.postValue(true)
                println("Usuario registrado en viewmodel")
            },
            onError = {
                _error.postValue(it.message ?: "Error desconocido")
            }
        )
    }


}