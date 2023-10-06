package com.monke.triviamasters.di.modules

import com.monke.triviamasters.data.repository.RegistrationRepositoryImpl
import com.monke.triviamasters.domain.repositories.RegistrationRepository
import dagger.Binds
import dagger.Module

@Module
abstract class RegistrationModule {

    @Binds
    abstract fun bindsRegistrationRepository(
        repositoryImpl: RegistrationRepositoryImpl
    ): RegistrationRepository
}