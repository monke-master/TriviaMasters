package com.monke.triviamasters.di.components

import com.monke.triviamasters.di.MainFragmentScope
import com.monke.triviamasters.di.modules.GameModule
import dagger.Subcomponent


@MainFragmentScope
@Subcomponent(modules = [GameModule::class])
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }
}