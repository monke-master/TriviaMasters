package com.monke.triviamasters.ui.signUpFeature

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentPasswordBinding
import com.monke.triviamasters.ui.loginFeature.LoginFragment
import com.monke.triviamasters.ui.loginFeature.signUpFeature.PasswordViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.math.sign


class PasswordFragment : Fragment() {

    @Inject
    lateinit var factory: PasswordViewModel.Factory
    private val viewModel: PasswordViewModel by viewModels { factory }

    private var binding: FragmentPasswordBinding? = null
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPasswordBinding.inflate(layoutInflater, container, false)
        (parentFragment?.parentFragment as LoginFragment).loginComponent.inject(this)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)
        setupPasswordEditText()
        setupRepeatedPasswordEditText()
        setupSignUpButton()
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

    private fun setupRepeatedPasswordEditText() {
        binding?.editTextRepeatedPassword?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                text?.let { viewModel.setRepeatedPassword(text.toString()) }
            }
        })
    }

    private fun setupSignUpButton() {
        val signUpBtn = binding?.btnSignUp
        signUpBtn?.setOnClickListener {
            viewModel.savePassword()
            navController.navigate(R.id.action_passwordFragment_to_createPlayerFragment)
        }
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.isPasswordValid.collect { isValid -> signUpBtn?.isEnabled = isValid }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}