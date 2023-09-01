package com.monke.triviamasters.ui.createPlayerFeature

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.View.OnFocusChangeListener
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.monke.triviamasters.MainActivity
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentCreatePlayerBinding
import com.monke.triviamasters.ui.loginFeature.LoginFragment
import com.monke.triviamasters.ui.loginFeature.createPlayerFeature.CreatePlayerViewModel
import javax.inject.Inject


class CreatePlayerFragment : Fragment() {


    @Inject
    lateinit var factory: CreatePlayerViewModel.Factory
    private val viewModel: CreatePlayerViewModel by viewModels { factory }
    private var binding: FragmentCreatePlayerBinding? = null


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentCreatePlayerBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val navController = (activity as MainActivity).mainNavController

        (parentFragment?.parentFragment as LoginFragment).loginComponent.inject(this)

        binding?.editTextUsername?.setOnFocusChangeListener { _, hasFocus ->
            if (!hasFocus) {
                binding?.editTextUsername?.let {
                    viewModel.setUsername(it.text.toString())
                }

            }
        }

        binding?.btnSignIn?.setOnClickListener {
            navController.navigate(R.id.action_loginFragment_to_mainFragment)
            viewModel.savePlayer()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}