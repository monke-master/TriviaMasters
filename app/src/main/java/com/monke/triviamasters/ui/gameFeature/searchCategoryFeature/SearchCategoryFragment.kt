package com.monke.triviamasters.ui.gameFeature.searchCategoryFeature

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.fragment.app.setFragmentResult
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentSearchCategoryBinding
import com.monke.triviamasters.ui.gameFeature.GameFragment
import com.monke.triviamasters.ui.gameFeature.ownGameFeature.OwnGameFragment
import com.monke.triviamasters.ui.recyclerViewUtils.VerticalSpaceItemDecoration
import com.monke.triviamasters.utils.toBundleString
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchCategoryFragment : Fragment() {

    @Inject
    lateinit var factory: SearchCategoryViewModel.Factory
    private val viewModel: SearchCategoryViewModel by viewModels { factory }

    private var binding: FragmentSearchCategoryBinding? = null

    private var requestKey: String? = null

    companion object {

        // Bundle-ключ для ключа запроса результата фрагмента
        const val REQUEST_KEY_BUNDLE = "request_key_bundle"
        // Bundle-ключ для передачи результата
        const val CATEGORIES_LIST_KEY = "categories_list_key"

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchCategoryBinding.inflate(inflater, container, false)
        (parentFragment?.parentFragment as GameFragment).gameComponent.inject(this)
        requestKey = arguments?.getString(REQUEST_KEY_BUNDLE)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategoriesRecyclerView()
        setupCategoryTitleEditText()
        setupDoneButton()
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

    private fun setupDoneButton() {
        binding?.btnDone?.setOnClickListener { btn ->
            requestKey?.let { key ->
                setFragmentResult(
                    requestKey = key,
                    result = bundleOf(
                        CATEGORIES_LIST_KEY to
                                viewModel.selectedCategories().map { it.toBundleString() }
                    )
                )
            }
            btn.findNavController().popBackStack()
        }
    }

}