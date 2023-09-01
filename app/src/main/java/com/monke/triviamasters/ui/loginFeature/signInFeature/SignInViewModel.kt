package com.monke.triviamasters.ui.loginFeature.signInFeature

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.monke.triviamasters.di.LoginFragmentScope
import javax.inject.Inject

class SignInViewModel @Inject constructor(): ViewModel() {

    class Factory @Inject constructor(): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return SignInViewModel() as T
        }
    }
}

