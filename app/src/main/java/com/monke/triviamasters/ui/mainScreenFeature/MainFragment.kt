package com.monke.triviamasters.ui.mainScreenFeature

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.NavController
import androidx.navigation.NavHost
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.monke.triviamasters.App
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentMainBinding
import com.monke.triviamasters.di.components.MainComponent

class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null
    lateinit var mainComponent: MainComponent

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentMainBinding.inflate(
            inflater,
            container,
            false
        )
        mainComponent = (activity?.application as App).appComponent.mainComponent().create()
        return binding?.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        val navHostFragment = childFragmentManager.findFragmentById(R.id.fragment_container)

        if (navHostFragment != null) {
            val navController = (navHostFragment as NavHostFragment).navController
            binding?.bottomNavigationView?.setupWithNavController(navController)
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}