package com.monke.triviamasters.ui.gameFeature.searchCategoryFeature

import com.monke.triviamasters.domain.useCases.category.GetCategoriesUseCase
import com.monke.triviamasters.domain.useCases.category.SelectCategoryUseCase
import com.monke.triviamasters.domain.useCases.game.CreateGameWithSettingsUseCase
import javax.inject.Inject

data class SearchCategoryUseCases @Inject constructor(
    val getCategoriesUseCase: GetCategoriesUseCase,
    val selectCategoryUseCase: SelectCategoryUseCase,
    val createGameWithSettingsUseCase: CreateGameWithSettingsUseCase
)