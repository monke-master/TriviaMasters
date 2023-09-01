package com.monke.triviamasters.di.components

import com.monke.triviamasters.di.MainFragmentScope
import dagger.Subcomponent

@Subcomponent
@MainFragmentScope
interface MainComponent {

    @Subcomponent.Factory
    interface Factory {
        fun create(): MainComponent
    }
}