package com.monke.triviamasters.ui.gameFeature

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import com.monke.triviamasters.App
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentGameBinding
import com.monke.triviamasters.di.components.GameComponent


/**
 * Главный фрагмент игры
 * @property gameComponent
 */
class GameFragment : Fragment() {

    lateinit var gameComponent: GameComponent

    private var binding: FragmentGameBinding? = null

    companion object {
        const val START_DESTINATION_BUNDLE = "start destination"
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        gameComponent = (activity?.application as App).appComponent.gameComponent().create()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGameBinding.inflate(inflater, container, false)
        // Устанавливает стартовый фрагмент
        arguments?.getInt(START_DESTINATION_BUNDLE)?.let {
            val navGraph = childFragmentManager
                .findFragmentById(R.id.fragment_container)?.findNavController()?.navigate(it)
            //navGraph?.setStartDestination(it)
        }
        return binding?.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {

    }

}