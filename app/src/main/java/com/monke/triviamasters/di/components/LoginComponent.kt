package com.monke.triviamasters.di.components

import com.monke.triviamasters.di.LoginFragmentScope
import com.monke.triviamasters.ui.createPlayerFeature.CreatePlayerFragment
import com.monke.triviamasters.ui.loginFeature.LoginFragment
import com.monke.triviamasters.ui.loginFeature.signInFeature.SignInFragment
import com.monke.triviamasters.ui.signUpFeature.EmailFragment
import com.monke.triviamasters.ui.signUpFeature.PasswordFragment
import dagger.Subcomponent

@Subcomponent
@LoginFragmentScope
interface LoginComponent {


    fun inject(signInFragment: SignInFragment)

    fun inject(emailFragment: EmailFragment)

    fun inject(passwordFragment: PasswordFragment)

    fun inject(createPlayerFragment: CreatePlayerFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): LoginComponent
    }

}