package com.monke.triviamasters.di.components

import com.monke.triviamasters.di.GameFragmentScope
import com.monke.triviamasters.di.modules.GameModule
import com.monke.triviamasters.di.modules.TriviaApiModule
import com.monke.triviamasters.ui.gameFeature.extraHardFeature.ExtraHardFragment
import com.monke.triviamasters.ui.gameFeature.fullyRandomFeature.FullyRandomFragment

import com.monke.triviamasters.ui.gameFeature.ownGameFeature.OwnGameFragment
import com.monke.triviamasters.ui.gameFeature.searchCategoryFeature.SearchCategoryFragment
import com.monke.triviamasters.ui.gameFeature.triviaFeature.trivia.TriviaFragment
import com.monke.triviamasters.ui.gameFeature.triviaFeature.result.TriviaResultFragment
import com.monke.triviamasters.ui.mainScreenFeature.modesFeature.ModesFragment
import com.monke.triviamasters.ui.mainScreenFeature.profileFeature.ProfileFragment
import dagger.Subcomponent

@GameFragmentScope
@Subcomponent(modules = [
    GameModule::class,
    TriviaApiModule::class
])
interface GameComponent {

    fun inject(searchCategoryFragment: SearchCategoryFragment)

    fun inject(ownGameFragment: OwnGameFragment)

    fun inject(fullyRandomFragment: FullyRandomFragment)

    fun inject(extraHardFragment: ExtraHardFragment)

    fun inject(triviaFragment: TriviaFragment)

    fun inject(resultFragment: TriviaResultFragment)

    fun inject(modesFragment: ModesFragment)

    fun inject(profileFragment: ProfileFragment)

    @Subcomponent.Factory
    interface Factory {
        fun create(): GameComponent
    }
}