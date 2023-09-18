package com.monke.triviamasters.ui.gameFeature.ownGameFeature

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.monke.triviamasters.domain.models.Category
import javax.inject.Inject

class OwnGameViewModel : ViewModel() {

    private val _selectedCategories = ArrayList<Category>()
    val selectedCategories: List<Category> = _selectedCategories

    var maxPrice: Int? = null
    var minPrice: Int? = null
    var questionsAmount: Int? = null

    init {
        _selectedCategories.add(Category(12, "Kudj Kobain"))
    }

    fun removeCategory(category: Category) {
        _selectedCategories.remove(category)
    }

    fun addCategories(categories: List<Category>) {
        _selectedCategories.addAll(categories)
    }

    class Factory @Inject constructor(): ViewModelProvider.Factory {

        override fun <T : ViewModel> create(modelClass: Class<T>): T {
            return OwnGameViewModel() as T
        }

    }
}