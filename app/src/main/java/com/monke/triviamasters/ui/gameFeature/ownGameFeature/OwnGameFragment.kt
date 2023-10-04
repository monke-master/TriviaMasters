package com.monke.triviamasters.ui.gameFeature.ownGameFeature

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.google.android.material.chip.Chip
import com.monke.triviamasters.MainActivity
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentOwnGameBinding
import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.ui.components.LoadingDialog
import com.monke.triviamasters.ui.gameFeature.searchCategoryFeature.SearchCategoryFragment
import com.monke.triviamasters.ui.uiModels.UiState
import kotlinx.coroutines.launch
import javax.inject.Inject


/**
 * Фрагмент с подробной настройкой игры
 */
class OwnGameFragment : Fragment() {

    @Inject
    lateinit var factory: OwnGameViewModel.Factory
    private val viewModel: OwnGameViewModel by viewModels { factory }

    private var binding: FragmentOwnGameBinding? = null

    companion object {

        // Ключ для запроса результата выбора категорий
        const val REQUEST_CATEGORIES_KEY = "own_game_request"

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentOwnGameBinding.inflate(layoutInflater, container, false)

        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        (activity as MainActivity).gameComponent.inject(this)
        collectUiState()
        setupCategoriesChips()
        setupAddCategoryBtn()
        setupMinPriceEditText()
        setupMaxPriceEditText()
        setupQuestionsAmountEditText()
        setupStartButton()
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
                        is UiState.Success -> {}
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

    private fun setupCategoriesChips() {
        viewLifecycleOwner.lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.selectedCategories.collect { categories ->
                    categories?.let { setCategoryChips(it) }
                }
            }
        }
    }

    // Кнопка добавление категории
    private fun setupAddCategoryBtn() {
        binding?.btnAddCategory?.setOnClickListener {
            it.findNavController().navigate(
                resId = R.id.action_ownGameFragment_to_searchCategoryFragment,
                args = bundleOf(
                    SearchCategoryFragment.REQUEST_KEY_BUNDLE to REQUEST_CATEGORIES_KEY)
            )
        }
    }

    private fun setCategoryChips(categories: List<Category>) {
        binding?.chipsCategories?.let {
            it.removeViews(0, it.childCount - 1)
            addCategoryChips(categories)
        }
    }

    // Добавление несколько чипов с категориями
    private fun addCategoryChips(categories: List<Category>) {
        for (category in categories) {
            addCategoryChip(category)
        }
    }

    // Добавление чипа с категорией
    private fun addCategoryChip(category: Category) {
        val chip = Chip(activity)
        chip.text = category.title
        binding?.chipsCategories?.addView(chip, 0)
        chip.setOnCloseIconClickListener {
            binding?.chipsCategories?.removeView(it)
            viewModel.removeCategory(category)
        }
    }

    // Ввод максимальной цены вопроса
    private fun setupMaxPriceEditText() {
        val editText = binding?.editTextMaxPrice
        editText?.setText(viewModel.maxPrice?.toString() ?: "")
        editText?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                if (!text.isNullOrBlank())
                    viewModel.maxPrice = text.toString().toInt()
            }
        })
    }

    // Ввод минимальной цены вопроса
    private fun setupMinPriceEditText() {
        val editText = binding?.editTextMinPrice
        editText?.setText(viewModel.minPrice?.toString() ?: "")
        binding?.editTextMinPrice?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                if (!text.isNullOrBlank())
                    viewModel.minPrice = text.toString().toInt()
            }
        })
    }

    // Ввод количества вопросов
    private fun setupQuestionsAmountEditText() {
        val editText = binding?.editTextQuestionsAmount
        editText?.setText(viewModel.questionsAmount.toString())
        binding?.editTextQuestionsAmount?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                if (!text.isNullOrBlank())
                    viewModel.questionsAmount = text.toString().toInt()
            }
        })
    }

    private fun setupStartButton() {
        binding?.btnStart?.setOnClickListener {
            viewModel.createGame()
        }
    }


}