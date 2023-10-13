package com.monke.triviamasters.ui.loginFeature.createPlayerFeature

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.monke.triviamasters.domain.useCases.email.GetConfirmationStatusUseCase
import com.monke.triviamasters.domain.useCases.player.CreatePlayerUseCase
import com.monke.triviamasters.domain.useCases.user.SignUpUseCase
import com.monke.triviamasters.ui.uiModels.UiState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import java.util.Calendar
import javax.inject.Inject

class CreatePlayerViewModel(
    private val createPlayerUseCase: CreatePlayerUseCase,
    private val getConfirmationStatusUseCase: GetConfirmationStatusUseCase,
    private val signUpUseCase: SignUpUseCase
): ViewModel() {

    private val _playerUsername = MutableStateFlow("")
    val playerUsername = _playerUsername.asStateFlow()

    private val _uiState = MutableStateFlow<UiState?>(null)
    val uiState = _uiState.asStateFlow()

    init {
        Log.d("CreatePlayerViewModel", "init block")
    }

    fun savePlayer() {
        viewModelScope.launch {
            val player = createPlayerUseCase.execute(
                username = _playerUsername.value,
                startedPlayingDate = Calendar.getInstance().timeInMillis
            )
            if (getConfirmationStatusUseCase.execute().value) {
                _uiState.value = UiState.Loading
                val res = signUpUseCase.execute(player)
                if (res.isFailure) {
                    res.exceptionOrNull()?.let { _uiState.value = UiState.Error(it) }
                    return@launch
                }
                _uiState.value = UiState.Success()
            } else
                _uiState.value = UiState.Success()
        }
    }


    fun setUsername(username: String) {
        _playerUsername.value = username
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("CreatePlayerViewModel", "is cleared")
    }

    class Factory @Inject constructor(
        private val createPlayerUseCase: CreatePlayerUseCase,
        private val getConfirmationStatusUseCase: GetConfirmationStatusUseCase,
        private val signUpUseCase: SignUpUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CreatePlayerViewModel(
                createPlayerUseCase = createPlayerUseCase,
                getConfirmationStatusUseCase = getConfirmationStatusUseCase,
                signUpUseCase = signUpUseCase
            ) as T
        }
    }



}