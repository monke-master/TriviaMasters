package com.monke.triviamasters.ui.gameFeature.triviaFeature.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.monke.triviamasters.MainActivity
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentTriviaResultBinding
import com.monke.triviamasters.ui.components.ResultRecyclerAdapter
import com.monke.triviamasters.ui.recyclerViewUtils.VerticalSpaceItemDecoration
import com.monke.triviamasters.ui.uiModels.ItemResultUI
import javax.inject.Inject

class TriviaResultFragment : Fragment() {


    @Inject
    lateinit var factory: TriviaResultViewModel.Factory
    private val viewModel: TriviaResultViewModel by viewModels { factory }

    private var binding: FragmentTriviaResultBinding? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        (activity as MainActivity).gameComponent.inject(this)
        binding = FragmentTriviaResultBinding.inflate(inflater, container, false)
        return binding?.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupRecyclerView()
        setupFinishBtn()
    }

    private fun setupFinishBtn() {
        binding?.btnFinish?.setOnClickListener {
            it.findNavController().navigate(R.id.action_triviaResultFragment_to_mainFragment)
        }
    }


    private fun setupRecyclerView() {
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
                title = getString(R.string.correct_answers),
                value = viewModel.game.correctAnswers
            ),
            ItemResultUI(
                title = getString(R.string.time_spent),
                value = (viewModel.game.timeSpent / 1000).toInt()
            ),
            ItemResultUI(
                title = getString(R.string.points_earned),
                value = viewModel.game.pointsEarned
            ),
        )
        adapter.resultItemsList = resultItems.toList()

    }

}