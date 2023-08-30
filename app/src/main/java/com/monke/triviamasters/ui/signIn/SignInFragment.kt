package com.monke.triviamasters.ui.signIn

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentSignInBinding


class SignInFragment : Fragment() {


    private var binding: FragmentSignInBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSignInBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navController = Navigation.findNavController(view)
        binding?.btnSignIn?.setOnClickListener {
            navController.navigate(R.id.action_signInFragment_to_mainFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}