package com.monke.triviamasters.ui.mainScreenFeature.profileFeature

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.monke.triviamasters.MainActivity
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentProfileBinding
import com.monke.triviamasters.domain.models.Player
import com.monke.triviamasters.domain.models.Statistics
import com.monke.triviamasters.ui.components.ResultRecyclerAdapter
import com.monke.triviamasters.ui.recyclerViewUtils.VerticalSpaceItemDecoration
import com.monke.triviamasters.ui.uiModels.ItemResultUI
import com.monke.triviamasters.utils.formatDate
import kotlinx.coroutines.launch
import javax.inject.Inject


class ProfileFragment : Fragment() {

    @Inject
    lateinit var factory: ProfileViewModel.Factory
    private val viewModel: ProfileViewModel by viewModels { factory }

    private var binding: FragmentProfileBinding? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)

        (activity as MainActivity).gameComponent.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(inflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.player.collect { player ->
                    player?.let {
                        setupRecyclerView(it.statistics)
                        setupPlayerData(it)
                    }

                }
            }
        }
    }

    private fun setupPlayerData(player: Player) {
        val formattedDate = formatDate(player.startedPlayingDate)
        binding?.textPlayerDate?.text = "${getString(R.string.started_playing)}\n $formattedDate"
        binding?.textUsername?.text = player.username
    }

    private fun setupRecyclerView(stats: Statistics) {
        // Инициализация адаптера
        val recyclerView = binding?.recyclerViewResults
        val adapter = ResultRecyclerAdapter()
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        // Добавление декораторов
        val dividerItemDecoration = DividerItemDecoration(
            requireContext(),
            DividerItemDecoration.VERTICAL
        )
        ContextCompat.getDrawable(
            requireContext(),
            R.drawable.shape_divider
        )?.let {
            dividerItemDecoration.setDrawable(it)
        }
        recyclerView?.addItemDecoration(dividerItemDecoration)

        // Добавление отступов между элементами
        val verticalSpaceItemDecoration = VerticalSpaceItemDecoration(
            verticalPadding = resources.getDimensionPixelSize(R.dimen.defaultListPadding),
            horizontalPadding = resources.getDimensionPixelSize(R.dimen.defaultHorizontalMargin)
        )
        recyclerView?.addItemDecoration(verticalSpaceItemDecoration)


        val resultItems = arrayOf(
            ItemResultUI(
                title = getString(R.string.score),
                value = stats.score
            ),
            ItemResultUI(
                title = getString(R.string.correct_answers),
                value = stats.correctAnswers
            ),
            ItemResultUI(
                title = getString(R.string.games_played),
                value = stats.gamesPlayed
            ),
            ItemResultUI(
                title = getString(R.string.questions_answered),
                value = stats.questionsAnswered
            ),
        )
        adapter.resultItemsList = resultItems.toList()

    }


}