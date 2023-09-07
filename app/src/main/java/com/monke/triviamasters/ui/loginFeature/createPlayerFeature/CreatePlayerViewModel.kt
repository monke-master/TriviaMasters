package com.monke.triviamasters.ui.loginFeature.createPlayerFeature

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.monke.triviamasters.domain.useCases.CreatePlayerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import java.util.Calendar
import javax.inject.Inject

class CreatePlayerViewModel(
    private val createPlayerUseCase: CreatePlayerUseCase
): ViewModel() {

    private val _playerUsername = MutableStateFlow("")
    val playerUsername = _playerUsername.asStateFlow()

    fun savePlayer() {
        createPlayerUseCase.execute(
            username = _playerUsername.value,
            startedPlayingDate = Calendar.getInstance().timeInMillis
        )
    }

    fun setUsername(username: String) {
        _playerUsername.value = username
    }

    override fun onCleared() {
        super.onCleared()
        Log.d("CreatePlayerViewModel", "is cleared")
    }

    class Factory @Inject constructor(
        private val createPlayerUseCase: CreatePlayerUseCase
    ) : ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return CreatePlayerViewModel(createPlayerUseCase) as T
        }
    }



}