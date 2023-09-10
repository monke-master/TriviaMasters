package com.monke.triviamasters.ui.loginFeature.signInFeature

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.Navigation
import com.monke.triviamasters.MainActivity
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentSignInBinding
import com.monke.triviamasters.domain.exceptions.IncorrectPasswordException
import com.monke.triviamasters.ui.loginFeature.LoginFragment
import com.monke.triviamasters.ui.uiModels.UiState
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
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

        setupPasswordEditText()
        setupEmailEditText()
        setupSignInButton()

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        is UiState.Error -> {
                            val message = when(state.exception) {
                                is IncorrectPasswordException ->
                                    getString(R.string.incorrect_password)
                                else -> "ХЗ"
                            }
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        }
                        UiState.Loading -> {
                        }
                        is UiState.Success -> {
                            navController.navigate(R.id.action_loginFragment_to_mainFragment)
                        }
                        else -> {}
                    }
                }
            }
        }

    }

    private fun setupPasswordEditText() {
        binding?.editTextPassword?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                text?.let { viewModel.setPassword(text.toString()) }
            }
        })
    }

    private fun setupEmailEditText() {
        binding?.editTextEmail?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                text?.let { viewModel.setEmail(text.toString()) }
            }

        })
    }

    private fun setupSignInButton() {
        val signInButton =  binding?.btnSignIn
        signInButton?.setOnClickListener {
            viewModel.signIn()
        }
    }



    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}