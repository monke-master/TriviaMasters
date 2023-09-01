package com.monke.triviamasters.di.components

import com.monke.triviamasters.di.GameFragmentScope
import dagger.Subcomponent

@GameFragmentScope
@Subcomponent
interface GameComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): GameComponent
    }
}