package com.monke.triviamasters.ui.signUpFeature

import android.opengl.Visibility
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Patterns
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentEmailBinding
import com.monke.triviamasters.di.components.LoginComponent
import com.monke.triviamasters.ui.loginFeature.LoginFragment
import com.monke.triviamasters.ui.loginFeature.signUpFeature.ConfirmEmailDialog
import com.monke.triviamasters.ui.loginFeature.signUpFeature.EmailViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject


class EmailFragment : Fragment() {

    @Inject
    lateinit var factory: EmailViewModel.Factory
    private val viewModel: EmailViewModel by viewModels { factory }

    private var binding: FragmentEmailBinding? = null
    lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentEmailBinding.inflate(layoutInflater, container, false)
        (parentFragment?.parentFragment as LoginFragment).loginComponent.inject(this)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        setupNextButton()
        setupEmailEditText()
    }

    private fun setupNextButton() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.email.collect { email ->
                    binding?.btnNext?.isEnabled = email.isNotEmpty()
                        //Patterns.EMAIL_ADDRESS.matcher(email).matches()
                }
            }
        }
        binding?.btnNext?.setOnClickListener {
            viewModel.saveEmail()
            showConfirmationDialog(
                onDismiss = {
                    navController.navigate(R.id.action_emailFragment_to_passwordFragment)
                }
            )
        }
    }

    private fun setupEmailEditText() {
        binding?.editTextEmail?.setText(viewModel.email.value)
        binding?.editTextEmail?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                text?.let {
                    viewModel.setEmail(text.toString())
                }
            }
        })
    }


    private fun showConfirmationDialog(onDismiss: () -> Unit) {
        val dialog = ConfirmEmailDialog()
        dialog.show(childFragmentManager, "")
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.emailConfirmed.collect { isConfirmed ->
                    if (isConfirmed) {
                        dialog.dismiss()
                        onDismiss()
                    }
                }
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}