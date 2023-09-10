package com.monke.triviamasters.di.modules

import com.monke.triviamasters.data.UserRepositoryImpl
import com.monke.triviamasters.domain.repositories.UserRepository
import dagger.Binds
import dagger.Module

@Module
abstract class UserModule {

    @Binds
    abstract fun bindUserRepository(repositoryImpl: UserRepositoryImpl): UserRepository
}