package com.monke.triviamasters.ui.modesFeature

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentModesBinding
import com.monke.triviamasters.ui.recyclerViewUtils.VerticalSpaceItemDecoration
import com.monke.triviamasters.utils.dp

class ModesFragment : Fragment() {

    private var binding: FragmentModesBinding? = null

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

        configureModesRW()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }

    // Настройка recycler view с режимами игры
    private fun configureModesRW() {
        val adapter = ModesRWAdapter(initModes())
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

    // Инициализация режимов игры
    private fun initModes(): List<Mode> {
        val modes = ArrayList<Mode>()
        modes.addAll(
            listOf(
                Mode.SearchCategories(
                    getString(R.string.searchCategories),
                    "No descr"
                ) {},
                Mode.FullyRandom(
                    getString(R.string.fullyRandom),
                    "No descr"
                ) {},
                Mode.StrengthTest(
                    getString(R.string.strengthTest),
                    "No descr"
                ) {},
                Mode.OwnGame(
                    getString(R.string.ownGame),
                    "No descr"
                ) {},
            )
        )
        return modes
    }


}