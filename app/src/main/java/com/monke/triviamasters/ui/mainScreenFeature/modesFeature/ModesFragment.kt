package com.monke.triviamasters.ui.mainScreenFeature.modesFeature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.monke.triviamasters.MainActivity
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentModesBinding
import com.monke.triviamasters.domain.models.GameMode
import com.monke.triviamasters.ui.gameFeature.questionFeature.ModeDescriptionFragment
import com.monke.triviamasters.ui.modesFeature.ModesRecyclerAdapter
import com.monke.triviamasters.ui.recyclerViewUtils.VerticalSpaceItemDecoration
import com.monke.triviamasters.ui.uiModels.GameModeUI

class ModesFragment : Fragment() {

    private var binding: FragmentModesBinding? = null
    private lateinit var mainNavController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModesBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupModesRW()
        setupOwnGameFab()
        mainNavController = (activity as MainActivity).mainNavController
    }

    private fun setupOwnGameFab() {
        binding?.btnOwnGame?.setOnClickListener {
            mainNavController.navigate(
                R.id.action_mainFragment_to_gameFragment,
                bundleOf(Pair(
                    ModeDescriptionFragment.BUNDLE_GAME_MODE_KEY,
                    GameMode.values().indexOf(GameMode.OwnGame)))
            )
        }
    }

    // Настройка recycler view с режимами игры
    private fun setupModesRW() {
        val adapter = ModesRecyclerAdapter(getModes())
        val recyclerView = binding?.rwModes
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        // Добавление декораторов
//        var dividerItemDecoration = DividerItemDecoration(
//            requireContext(),
//            DividerItemDecoration.VERTICAL
//        )
//        dividerItemDecoration.setDrawable(
//            resources.getDrawable(
//                R.drawable.shape_divider,
//                requireContext().theme
//            )
//        )
//        recyclerView?.addItemDecoration(dividerItemDecoration)

        val verticalPadding = resources.getDimensionPixelSize(R.dimen.defaultListPadding)
        val horizontalPadding = resources.getDimensionPixelSize(R.dimen.defaultHorizontalMargin)
        var verticalSpaceItemDecoration = VerticalSpaceItemDecoration(
            verticalPadding = verticalPadding,
            horizontalPadding = horizontalPadding
        )
        recyclerView?.addItemDecoration(verticalSpaceItemDecoration)
    }

    private fun getModes(): Array<GameModeUI> {
        val onClick: (Int) -> Unit = {
            mainNavController.navigate(
                R.id.action_mainFragment_to_gameFragment,
                bundleOf(Pair(ModeDescriptionFragment.BUNDLE_GAME_MODE_KEY, it))
            )
        }
        return arrayOf(
            GameModeUI(
                mode = GameMode.Category,
                title = getString(R.string.search_categories),
                onClick = onClick,
                index = GameMode.values().indexOf(GameMode.Category)
            ),
            GameModeUI(
                mode = GameMode.ExtraHard,
                title = getString(R.string.extra_hard),
                onClick = onClick,
                index = GameMode.values().indexOf(GameMode.ExtraHard)
            ),
            GameModeUI(
                mode = GameMode.FullyRandom,
                title = getString(R.string.fully_random),
                onClick = onClick,
                index = GameMode.values().indexOf(GameMode.FullyRandom)
            ),
            GameModeUI(
                mode = GameMode.OwnGame,
                title = getString(R.string.own_game),
                onClick = onClick,
                index = GameMode.values().indexOf(GameMode.OwnGame)
            ),
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}