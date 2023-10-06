package com.monke.triviamasters.di.modules

import com.monke.triviamasters.data.repository.PlayerRepositoryImpl
import com.monke.triviamasters.domain.repositories.PlayerRepository
import dagger.Binds
import dagger.Module

@Module
abstract class PlayerModule {

    @Binds
    abstract fun bindPlayerRepository(repositoryImpl: PlayerRepositoryImpl): PlayerRepository
}