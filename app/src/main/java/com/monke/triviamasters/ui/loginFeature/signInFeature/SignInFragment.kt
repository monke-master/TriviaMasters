package com.monke.triviamasters.ui.loginFeature.signInFeature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import com.monke.triviamasters.MainActivity
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentSignInBinding
import com.monke.triviamasters.ui.loginFeature.LoginFragment
import javax.inject.Inject


class SignInFragment : Fragment() {

    @Inject
    lateinit var factory: SignInViewModel.Factory
    private val viewModel: SignInViewModel by viewModels {
        factory
    }

    private var binding: FragmentSignInBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater, container, false)
        (parentFragment?.parentFragment as LoginFragment).loginComponent.inject(this)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = (activity as MainActivity).mainNavController

        binding?.btnSignIn?.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_mainFragment)
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}