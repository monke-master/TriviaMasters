package com.monke.triviamasters.ui.gameFeature.ownGameFeature

import android.os.Bundle
import android.support.v4.os.IResultReceiver._Parcel
import android.view.ContextThemeWrapper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentFactory
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.google.android.material.chip.Chip
import com.monke.triviamasters.R
import com.monke.triviamasters.databinding.FragmentOwnGameBinding
import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.ui.gameFeature.GameFragment
import kotlinx.coroutines.launch
import javax.inject.Inject


class OwnGameFragment : Fragment() {

    @Inject
    lateinit var factory: OwnGameViewModel.Factory
    private val viewModel: OwnGameViewModel by viewModels { factory }

    private var binding: FragmentOwnGameBinding? = null


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
    }

    private fun setupCategoriesChips() {
        for (category in viewModel.selectedCategories) {
            addCategoryChip(category)
        }
    }

    private fun addCategoryChip(category: Category) {
        val chip = Chip(activity)
        chip.text = category.title
        binding?.chipsCategories?.addView(chip, 0)
        chip.setOnCloseIconClickListener {
            binding?.chipsCategories?.removeView(it)
            viewModel.removeCategory(category)
        }
    }






}