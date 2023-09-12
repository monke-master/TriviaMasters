package com.monke.triviamasters.di.components

import com.monke.triviamasters.di.MainFragmentScope
import com.monke.triviamasters.di.modules.GameModule
import com.monke.triviamasters.ui.mainScreenFeature.modesFeature.ModesFragment
import dagger.Subcomponent


@MainFragmentScope
@Subcomponent(modules = [GameModule::class])
interface MainComponent {

    fun inject(modesFragment: ModesFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }
}