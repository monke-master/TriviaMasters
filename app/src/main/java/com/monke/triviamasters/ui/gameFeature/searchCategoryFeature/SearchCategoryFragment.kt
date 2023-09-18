package com.monke.triviamasters.ui.gameFeature.searchCategoryFeature

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
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
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentSearchCategoryBinding
import com.monke.triviamasters.ui.gameFeature.GameFragment
import com.monke.triviamasters.ui.recyclerViewUtils.VerticalSpaceItemDecoration
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchCategoryFragment : Fragment() {

    @Inject
    lateinit var factory: SearchCategoryViewModel.Factory
    private val viewModel: SearchCategoryViewModel by viewModels { factory }

    private var binding: FragmentSearchCategoryBinding? = null





    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchCategoryBinding.inflate(inflater, container, false)
        (parentFragment?.parentFragment as GameFragment).gameComponent.inject(this)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategoriesRecyclerView()
        setupCategoryTitleEditText()
    }

    private fun setupCategoryTitleEditText() {
        val titleEditText = binding?.editTextTitle
        titleEditText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                text?.let { viewModel.searchCategory(it.toString()) }
            }
        })

    }

    private fun setupCategoriesRecyclerView() {
        // Инициализация адаптера
        val recyclerView = binding?.recyclerViewCategories
        val adapter = CategoryRecyclerAdapter(onCategoryClicked = { categoryUi ->
            viewModel.setCategorySelected(
                categoryUi = categoryUi,
                isSelected = !categoryUi.isSelected
            )
        })
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

        // Подписывается на изменение списка найденных категорий
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categories.collect { categories ->
                    adapter.categoriesList = categories
                }
            }
        }

    }

}