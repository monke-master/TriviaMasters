package com.monke.triviamasters.ui.gameFeature.searchCategoryFeature

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentSearchCategoryBinding
import com.monke.triviamasters.ui.gameFeature.GameFragment
import kotlinx.coroutines.flow.collect
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
        val recyclerView = binding?.recyclerViewCategories
        val adapter = CategoryRecyclerAdapter()
        recyclerView?.adapter = adapter
        recyclerView?.layoutManager = LinearLayoutManager(
            requireContext(),
            LinearLayoutManager.VERTICAL,
            false
        )

        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categories.collect { categories ->
                    adapter.categoriesList = categories
                }
            }
        }

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

    }



}