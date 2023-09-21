package com.monke.triviamasters.domain.useCases.category

import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.domain.repositories.GameRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class SaveSelectedCategoriesUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {

    suspend fun execute(categories: MutableCollection<Category>) {
        val oldCategories = gameRepository.getGameSettings().first()
            .selectedCategories?.toMutableList() ?: ArrayList()
        oldCategories.addAll(categories)
        val newGameSettings = gameRepository.getGameSettings().first()
            .copy(selectedCategories = oldCategories)
        gameRepository.saveGameSettings(newGameSettings)
    }

}