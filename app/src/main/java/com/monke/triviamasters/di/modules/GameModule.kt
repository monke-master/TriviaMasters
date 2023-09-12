package com.monke.triviamasters.di.modules

import com.monke.triviamasters.data.CategoryRepositoryImpl
import com.monke.triviamasters.data.GameRepositoryImpl
import com.monke.triviamasters.domain.repositories.CategoryRepository
import com.monke.triviamasters.domain.repositories.GameRepository
import dagger.Binds
import dagger.Module

@Module
abstract class GameModule {

    @Binds
    abstract fun bindCategoryRepository(repositoryImpl: CategoryRepositoryImpl): CategoryRepository

    @Binds
    abstract fun bindGameRepository(repositoryImpl: GameRepositoryImpl): GameRepository
}