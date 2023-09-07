package com.monke.triviamasters.ui.loginFeature.signUpFeature

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.monke.triviamasters.domain.useCases.IsPasswordValidUseCase
import com.monke.triviamasters.domain.useCases.SavePasswordUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

class PasswordViewModel @Inject constructor(
    private val savePasswordUseCase: SavePasswordUseCase,
    private val isPasswordValidUseCase: IsPasswordValidUseCase
) : ViewModel() {

    private val _password = MutableStateFlow("")
    val password = _password.asStateFlow()

    private val _repeatedPassword = MutableStateFlow("")
    val repeatedPassword = _repeatedPassword.asStateFlow()

    private val _isPasswordValid = MutableStateFlow(false)
    val isPasswordValid = _isPasswordValid.asStateFlow()

    init {
        Log.d("PasswordViewModel", "init block")
    }


    fun setPassword(password: String) {
        _password.value = password
        _isPasswordValid.value = isPasswordValidUseCase.execute(
            password = password,
            repeatedPassword = _repeatedPassword.value
        )
    }

    fun setRepeatedPassword(repeatedPassword: String) {
        _repeatedPassword.value = repeatedPassword
        _isPasswordValid.value = isPasswordValidUseCase.execute(
            password = _password.value,
            repeatedPassword = _repeatedPassword.value
        )
    }

    fun savePassword() {
        savePasswordUseCase.execute(_password.value)
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("PasswordViewModel", "is cleared")
    }

    class Factory @Inject constructor(
        private val savePasswordUseCase: SavePasswordUseCase,
        private val isPasswordValidUseCase: IsPasswordValidUseCase
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return PasswordViewModel(
                savePasswordUseCase = savePasswordUseCase,
                isPasswordValidUseCase = isPasswordValidUseCase
            ) as T
        }
    }

}