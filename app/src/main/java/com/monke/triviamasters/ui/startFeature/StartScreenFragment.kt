package com.monke.triviamasters.ui.startFeature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentStartBinding


class StartScreenFragment : Fragment() {

    private var binding: FragmentStartBinding? = null
    private lateinit var navController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentStartBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController = Navigation.findNavController(view)

        binding?.btnSignIn?.setOnClickListener {
            navController.navigate(R.id.action_startScreenFragment_to_signInFragment)
        }

        binding?.btnSignUp?.setOnClickListener {
            navController.navigate(R.id.action_startScreenFragment_to_emailFragment)
        }


        binding?.btnContinue?.setOnClickListener {
            navController.navigate(R.id.action_startScreenFragment_to_createPlayerFragment)
        }

    }

    override fun onDestroy() {
        super.onDestroy()

        binding = null
    }

}