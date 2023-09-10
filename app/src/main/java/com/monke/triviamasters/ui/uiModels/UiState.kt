package com.monke.triviamasters.ui.uiModels

import java.lang.Exception

sealed class UiState {

    object Loading : UiState()

    data class Error(
        val exception: Exception
    ): UiState()

    data class Success(
        val data: Any? = null
    ): UiState()

}