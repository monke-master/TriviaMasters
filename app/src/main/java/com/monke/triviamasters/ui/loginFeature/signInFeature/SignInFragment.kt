package com.monke.triviamasters.ui.loginFeature.signInFeature

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.monke.triviamasters.MainActivity
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentSignInBinding
import com.monke.triviamasters.domain.exceptions.IncorrectPasswordException
import com.monke.triviamasters.ui.components.LoadingDialog
import com.monke.triviamasters.ui.uiModels.UiState
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
        (activity as MainActivity).loginComponent.inject(this)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = (activity as MainActivity).mainNavController

        setupPasswordEditText()
        setupEmailEditText()
        setupSignInButton()

        // Подписывается на изменение состояния интерфейса
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        // В случае ошибки показывает toast
                        is UiState.Error -> {
                            val message = when(state.exception) {
                                is IncorrectPasswordException ->
                                    getString(R.string.incorrect_password)
                                else -> "ХЗ"
                            }
                            Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show()
                        }
                        // В случае загрузки показывает диалог с прогрессом
                        UiState.Loading -> {
                            showLoadingDialog()
                        }
                        // В случае успеха переходит на следующий фрагмет
                        is UiState.Success -> {
                            navController.navigate(R.id.action_login_to_main_fragment)
                        }
                        // При отсутствующем состоянии ничего не делает
                        null -> {}
                    }
                }
            }
        }

    }

    private fun showLoadingDialog() {
        val dialog = LoadingDialog()
        dialog.show(parentFragmentManager, dialog.tag)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    if (state !is UiState.Loading)
                        dialog.dismiss()
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