package com.monke.triviamasters.domain.useCases.category

import com.monke.triviamasters.domain.models.Category
import com.monke.triviamasters.domain.repositories.GameRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

class RemoveSelectedCategoryUseCase @Inject constructor(
    private val gameRepository: GameRepository
) {

    suspend fun execute(category: Category) {
        val categories = gameRepository.getGameSettings().first().selectedCategories?.toMutableList()
        categories?.remove(category)
        gameRepository.saveGameSettings(gameRepository.getGameSettings()
            .first().copy(selectedCategories = categories))
    }
}