package com.monke.triviamasters.ui.questionFeature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.findNavController
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentDescriptionBinding
import com.monke.triviamasters.domain.GameMode
import com.monke.triviamasters.ui.uiModels.GameModeUI


class DescriptionFragment : Fragment() {

    private lateinit var gameMode: GameMode
    private var binding: FragmentDescriptionBinding? = null

    companion object {
        const val BUNDLE_MODE_KEY = "mode index"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val modeIndex = arguments?.getInt(BUNDLE_MODE_KEY)
        modeIndex?.let {
            gameMode = GameMode.values()[modeIndex]
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentDescriptionBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        binding?.toolbar?.title = GameModeUI.getTitle(gameMode, requireContext())
        binding?.txtDescription?.text = GameModeUI.getDescription(gameMode, requireContext())
        setupNextButton()
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