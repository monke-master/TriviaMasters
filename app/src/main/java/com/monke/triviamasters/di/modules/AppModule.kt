package com.monke.triviamasters.di.modules

import com.monke.triviamasters.di.components.GameComponent
import com.monke.triviamasters.di.components.LoginComponent
import dagger.Module

@Module(
    subcomponents = [
        LoginComponent::class,
        GameComponent::class
    ]
)
class AppModule {}