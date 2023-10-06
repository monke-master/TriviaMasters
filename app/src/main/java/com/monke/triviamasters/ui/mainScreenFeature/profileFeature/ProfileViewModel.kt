package com.monke.triviamasters.ui.mainScreenFeature.profileFeature

import android.provider.ContactsContract.Profile
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.monke.triviamasters.domain.models.Player
import com.monke.triviamasters.domain.useCases.player.GetPlayerUseCase
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProfileViewModel (
    private val getPlayerUseCase: GetPlayerUseCase
): ViewModel() {

    private val _player = MutableStateFlow<Player?>(null)
    val player = _player.asStateFlow()


    init {
        viewModelScope.launch {
            _player.value = getPlayerUseCase.execute().first()
        }
    }



    class Factory @Inject constructor(
        private val getPlayerUseCase: GetPlayerUseCase
    ): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return ProfileViewModel(
                getPlayerUseCase = getPlayerUseCase
            ) as T
        }

    }
}