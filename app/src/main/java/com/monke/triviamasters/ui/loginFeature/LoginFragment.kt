package com.monke.triviamasters.ui.loginFeature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.monke.triviamasters.App
import com.monke.triviamasters.R
import com.monke.triviamasters.di.components.LoginComponent
import com.monke.triviamasters.ui.loginFeature.signInFeature.SignInViewModel
import dagger.Component
import javax.inject.Inject


class LoginFragment : Fragment() {

    lateinit var navController: NavController

    lateinit var loginComponent: LoginComponent

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        loginComponent = (activity?.application as App).appComponent.loginComponent().create()
        return inflater.inflate(R.layout.fragment_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()
    }

    override fun onDestroy() {
        super.onDestroy()
    }

}