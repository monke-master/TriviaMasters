package com.monke.triviamasters.ui.loginFeature.createPlayerFeature

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
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.monke.triviamasters.MainActivity
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentCreatePlayerBinding
import com.monke.triviamasters.ui.components.LoadingDialog
import com.monke.triviamasters.ui.uiModels.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject


class CreatePlayerFragment : Fragment() {


    @Inject
    lateinit var factory: CreatePlayerViewModel.Factory
    private val viewModel: CreatePlayerViewModel by viewModels { factory }
    private var binding: FragmentCreatePlayerBinding? = null
    private lateinit var navController: NavController


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatePlayerBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = (activity as MainActivity).mainNavController
        (activity as MainActivity).loginComponent.inject(this)

        setupSignInButton()
        setupUsernameEditText()
        collectUiState()
    }

    private fun setupUsernameEditText() {
        binding?.editTextUsername?.setText( viewModel.playerUsername.value)
        binding?.editTextUsername?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                viewModel.setUsername(text?.toString() ?: "")
                binding?.btnSignIn?.isEnabled = text?.toString()?.isNotEmpty() ?: false
            }
        })
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        UiState.Loading -> showLoadingDialog()
                        is UiState.Error ->
                            Toast.makeText(
                                requireContext(),
                                state.exception.message,
                                Toast.LENGTH_SHORT).
                            show()
                        is UiState.Success -> {
                            navController.navigate(R.id.action_login_to_main_fragment)
                        }
                        else -> {}
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

    private fun setupSignInButton() {
        binding?.btnSignIn?.setOnClickListener {
            viewModel.savePlayer()
        }
        binding?.btnSignIn?.isEnabled = viewModel.playerUsername.value.isNotEmpty()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}