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
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    private var binding: FragmentMainBinding? = null
    private val mainDestinations = listOf(
        R.id.modesFragment,
        R.id.profileFragment
    )

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

            navController.addOnDestinationChangedListener { controller, destination, bundle ->
                if (destination.id in mainDestinations) {
                    binding?.bottomNavigationView?.visibility = View.VISIBLE
                    binding?.toolbar?.visibility = View.VISIBLE
                } else {
                    binding?.bottomNavigationView?.visibility = View.GONE
                    binding?.toolbar?.visibility = View.GONE
                }

            }
        }

    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}