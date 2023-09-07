package com.monke.triviamasters.ui.loginFeature.signUpFeature

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.monke.triviamasters.domain.repositories.RegistrationRepository
import com.monke.triviamasters.domain.useCases.GetConfirmationStatusUseCase
import com.monke.triviamasters.domain.useCases.SaveEmailUseCase
import com.monke.triviamasters.domain.useCases.SendConfirmationLetterUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.lang.Thread.State
import javax.inject.Inject

class EmailViewModel(
    private val saveEmailUseCase: SaveEmailUseCase,
    private val sendConfirmationLetterUseCase: SendConfirmationLetterUseCase,
    private val getConfirmationStatusUseCase: GetConfirmationStatusUseCase,
) : ViewModel() {

    private val _email = MutableStateFlow<String>("")
    val email = _email.asStateFlow()

    var emailConfirmed = getConfirmationStatusUseCase.execute()

    init {
        Log.d("EmailViewModel", "init block")
    }

    fun setEmail(emailAddress: String) {
        _email.value = emailAddress
    }

    fun saveEmail() {
        saveEmailUseCase.execute(_email.value)
        viewModelScope.launch {
            sendConfirmationLetterUseCase.execute()

        }
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("CreatePlayerViewModel", "is cleared")
    }

    class Factory @Inject constructor(
        private val saveEmailUseCase: SaveEmailUseCase,
        private val sendConfirmationLetterUseCase: SendConfirmationLetterUseCase,
        private val getConfirmationStatusUseCase: GetConfirmationStatusUseCase,
        private val registrationRepository: RegistrationRepository
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return EmailViewModel(
                saveEmailUseCase = saveEmailUseCase,
                sendConfirmationLetterUseCase = sendConfirmationLetterUseCase,
                getConfirmationStatusUseCase = getConfirmationStatusUseCase,
            ) as T
        }

    }

}