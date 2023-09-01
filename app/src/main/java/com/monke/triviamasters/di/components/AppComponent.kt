package com.monke.triviamasters.di.components

import android.app.Application
import com.monke.triviamasters.di.AppScope
import com.monke.triviamasters.di.modules.AppModule
import com.monke.triviamasters.di.modules.PlayerModule
import dagger.BindsInstance
import dagger.Component


@Component(modules = [AppModule::class, PlayerModule::class])
@AppScope
interface AppComponent {

    fun loginComponent(): LoginComponent.Factory

    fun gameComponent(): GameComponent.Factory

    fun mainComponent(): MainComponent.Factory

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Application): Builder

        fun build() : AppComponent
    }
}