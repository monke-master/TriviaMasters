package com.monke.triviamasters.ui.gameFeature.descriptionFeature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.navigation.findNavController
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentModeDescriptionBinding
import com.monke.triviamasters.domain.models.GameMode
import com.monke.triviamasters.ui.gameFeature.GameFragment
import com.monke.triviamasters.ui.uiModels.GameModeUI
import javax.inject.Inject


class ModeDescriptionFragment : Fragment() {

    private lateinit var gameMode: GameMode
    private var binding: FragmentModeDescriptionBinding? = null

    private lateinit var navController: NavController

    @Inject
    lateinit var factory: ModeDescriptionViewModel.Factory
    private val viewModel: ModeDescriptionViewModel by viewModels { factory }

    companion object {
        const val BUNDLE_GAME_MODE_KEY = "mode index"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        (parentFragment?.parentFragment as GameFragment).gameComponent.inject(this)

        gameMode = viewModel.gameSettings.gameMode

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModeDescriptionBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)
        navController = view.findNavController()

        binding?.txtDescription?.text = GameModeUI.getDescription(gameMode, requireContext())
        setupNextButton()
        setupToolbar()

    }

    private fun setupToolbar() {
        binding?.toolbar?.setNavigationOnClickListener {
            navController.popBackStack()
        }
        binding?.toolbar?.title = GameModeUI.getTitle(gameMode, requireContext())
    }

    private fun setupNextButton() {
        binding?.btnStart?.setOnClickListener { view ->
            when(gameMode) {
                GameMode.OwnGame -> {
                    binding?.btnStart?.text = getString(R.string.next)
                    view.findNavController()
                        .navigate(R.id.action_descriptionFragment_to_ownGameFragment)
                }
                GameMode.Category -> {
                    binding?.btnStart?.text = getString(R.string.next)
                    view.findNavController()
                        .navigate(R.id.action_descriptionFragment_to_searchCategoryFragment)
                }
                GameMode.FullyRandom -> {
                    binding?.btnStart?.text = getString(R.string.start)
                    view.findNavController()
                        .navigate(R.id.action_descriptionFragment_to_questionFragment)
                }
                GameMode.ExtraHard -> {
                    binding?.btnStart?.text = getString(R.string.start)
                    view.findNavController()
                        .navigate(R.id.action_descriptionFragment_to_questionFragment)
                }
            }
        }
    }

}