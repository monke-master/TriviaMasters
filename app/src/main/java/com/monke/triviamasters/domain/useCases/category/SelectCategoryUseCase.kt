package com.monke.triviamasters.domain.useCases.category

import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.domain.repositories.GameRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SelectCategoryUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {

    suspend fun execute(category: Category) {
        val gameSettings = gameRepository.getGameSettings().first()
        val categoriesList = gameSettings.selectedCategories?.toMutableList() ?: ArrayList()
        if (category !in categoriesList)
            categoriesList.add(category)
        gameRepository.saveGameSettings(gameSettings.copy(selectedCategories = categoriesList))
    }
}