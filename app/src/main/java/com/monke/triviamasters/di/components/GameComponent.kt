package com.monke.triviamasters.di.components

import com.monke.triviamasters.di.GameFragmentScope
import com.monke.triviamasters.di.modules.GameModule
import com.monke.triviamasters.ui.gameFeature.GameFragment
import com.monke.triviamasters.ui.gameFeature.descriptionFeature.ModeDescriptionFragment
import com.monke.triviamasters.ui.gameFeature.searchCategoryFeature.SearchCategoryFragment
import dagger.Subcomponent

@GameFragmentScope
@Subcomponent(modules = [GameModule::class])
interface GameComponent {

    fun inject(searchCategoryFragment: SearchCategoryFragment)

    fun inject(ownGameFragment: GameFragment)

    fun inject(modeDescriptionFragment: ModeDescriptionFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): GameComponent
    }
}