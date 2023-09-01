package com.monke.triviamasters.ui.signUpFeature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentPasswordBinding


class PasswordFragment : Fragment() {

    private var binding: FragmentPasswordBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentPasswordBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val navigation = Navigation.findNavController(view)

        binding?.btnSignUp?.setOnClickListener {
            navigation.navigate(R.id.action_passwordFragment_to_createPlayerFragment)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }


}