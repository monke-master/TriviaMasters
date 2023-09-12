package com.monke.triviamasters.ui.mainScreenFeature.modesFeature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.navigation.NavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.monke.triviamasters.MainActivity
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentModesBinding
import com.monke.triviamasters.domain.models.GameMode
import com.monke.triviamasters.ui.mainScreenFeature.MainFragment
import com.monke.triviamasters.ui.modesFeature.ModesRecyclerAdapter
import com.monke.triviamasters.ui.recyclerViewUtils.VerticalSpaceItemDecoration
import com.monke.triviamasters.ui.uiModels.GameModeUI
import javax.inject.Inject

class ModesFragment : Fragment() {

    @Inject
    lateinit var factory: ModesViewModel.Factory
    private val viewModel: ModesViewModel by viewModels { factory }

    private var binding: FragmentModesBinding? = null
    private lateinit var mainNavController: NavController

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentModesBinding.inflate(inflater, container, false)
        (parentFragment?.parentFragment as MainFragment).mainComponent.inject(this)
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
            viewModel.saveGameMode(GameMode.OwnGame)
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

        // Добавление отступов между элементами
        val verticalSpaceItemDecoration = VerticalSpaceItemDecoration(
            verticalPadding = resources.getDimensionPixelSize(R.dimen.defaultListPadding),
            horizontalPadding = resources.getDimensionPixelSize(R.dimen.defaultHorizontalMargin)
        )
        recyclerView?.addItemDecoration(verticalSpaceItemDecoration)
    }

    private fun getModes(): Array<GameModeUI> {
        val onClick: (GameMode) -> Unit = { gameMode ->
            mainNavController.navigate(R.id.action_mainFragment_to_gameFragment)
            viewModel.saveGameMode(gameMode)
        }
        return arrayOf(
            GameModeUI(
                mode = GameMode.Category,
                title = getString(R.string.search_categories),
                onClick = onClick
            ),
            GameModeUI(
                mode = GameMode.ExtraHard,
                title = getString(R.string.extra_hard),
                onClick = onClick
            ),
            GameModeUI(
                mode = GameMode.FullyRandom,
                title = getString(R.string.fully_random),
                onClick = onClick
            ),
            GameModeUI(
                mode = GameMode.OwnGame,
                title = getString(R.string.own_game),
                onClick = onClick
            ),
        )
    }


    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

}