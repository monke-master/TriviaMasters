package com.monke.triviamasters.ui.gameFeature.ownGameFeature

import android.os.Bundle
import android.support.v4.os.IResultReceiver._Parcel
import android.text.Editable
import android.text.TextWatcher
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.setFragmentResultListener
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.findNavController
import com.google.android.material.chip.Chip
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentOwnGameBinding
import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.ui.gameFeature.GameFragment
import com.monke.triviamasters.ui.gameFeature.searchCategoryFeature.SearchCategoryFragment
import com.monke.triviamasters.utils.fromBundleString
import kotlinx.coroutines.launch
import javax.inject.Inject


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

        (parentFragment?.parentFragment as GameFragment).gameComponent.inject(this)
        setupCategoriesChips()
        setupAddCategoryBtn()
        setupMinPriceEditText()
        setupMaxPriceEditText()
        setupQuestionsAmountEditText()
    }


    private fun setupCategoriesChips() {
        for (category in viewModel.selectedCategories) {
            addCategoryChip(category)
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
            // Обработка результата выбора категории
            setFragmentResultListener(
                requestKey = REQUEST_CATEGORIES_KEY
            ) { _, result ->
                result.getStringArrayList(SearchCategoryFragment.CATEGORIES_LIST_KEY)?.let { list ->
                    val selectedCategories = list.map {
                            categoryStr -> Category.fromBundleString(categoryStr)
                    }
                    viewModel.addCategories(selectedCategories)
                    addCategoryChips(selectedCategories)
                }
            }
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
        binding?.editTextMinPrice?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {}

            override fun afterTextChanged(text: Editable?) {
                if (!text.isNullOrBlank())
                    viewModel.questionsAmount = text.toString().toInt()
            }
        })
    }


}