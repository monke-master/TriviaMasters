package com.monke.triviamasters.ui.gameFeature.searchCategoryFeature

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.monke.triviamasters.MainActivity
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentSearchCategoryBinding
import com.monke.triviamasters.ui.components.LoadingDialog
import com.monke.triviamasters.ui.gameFeature.ownGameFeature.OwnGameFragment
import com.monke.triviamasters.ui.recyclerViewUtils.VerticalSpaceItemDecoration
import com.monke.triviamasters.ui.uiModels.UiState
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Фрагмент с поиском и выбором категорий
 */
class SearchCategoryFragment : Fragment() {

    @Inject
    lateinit var factory: SearchCategoryViewModel.Factory
    private val viewModel: SearchCategoryViewModel by viewModels { factory }

    private var binding: FragmentSearchCategoryBinding? = null

    private var requestKey: String? = null

    companion object {

        // Ключ запроса
        const val REQUEST_KEY_BUNDLE = "request_key_bundle"

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentSearchCategoryBinding.inflate(inflater, container, false)
        (activity as MainActivity).gameComponent.inject(this)
        requestKey = arguments?.getString(REQUEST_KEY_BUNDLE)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        setupCategoriesRecyclerView()
        collectUiState()
    }

    private fun collectUiState() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    when (state) {
                        UiState.Loading -> showLoadingDialog()
                        is UiState.Error ->
                            Toast.makeText(
                                requireContext(),
                                state.exception.message,
                                Toast.LENGTH_SHORT).
                            show()
                        is UiState.Success -> {
                            binding?.root?.findNavController()
                                ?.navigate(R.id.action_searchCategoryFragment_to_questionFragment)
                        }
                        else -> {}
                    }
                }
            }
        }
    }

    private fun showLoadingDialog() {
        val dialog = LoadingDialog()
        dialog.show(parentFragmentManager, dialog.tag)
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiState.collect { state ->
                    if (state !is UiState.Loading)
                        dialog.dismiss()
                }
            }
        }
    }


    private fun setupCategoriesRecyclerView() {
        // Инициализация адаптера
        val recyclerView = binding?.recyclerViewCategories
        val adapter = CategoryRecyclerAdapter(
            onCategoryClicked = { category ->
                viewModel.selectCategory(category)
                when (requestKey) {
                    OwnGameFragment.REQUEST_CATEGORIES_KEY ->
                        binding?.root?.findNavController()?.popBackStack()
                    else -> viewModel.createGame()
                }
            }
        )
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

        // Подписывается на изменение списка категорий
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.categories.collectLatest { categories ->
                    adapter.submitData(categories)
                }
            }
        }
    }


}