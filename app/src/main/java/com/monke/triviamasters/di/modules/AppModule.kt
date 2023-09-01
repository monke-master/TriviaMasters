package com.monke.triviamasters.di.modules

import com.monke.triviamasters.di.components.GameComponent
import com.monke.triviamasters.di.components.LoginComponent
import com.monke.triviamasters.di.components.MainComponent
import dagger.Module

@Module(
    subcomponents = [
        LoginComponent::class,
        MainComponent::class,
        GameComponent::class
    ]
)
class AppModule {}