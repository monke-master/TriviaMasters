package com.monke.triviamasters.di.components

import android.app.Application
import com.monke.triviamasters.di.AppScope
import com.monke.triviamasters.di.modules.AppModule
import com.monke.triviamasters.di.modules.FirebaseModule
import com.monke.triviamasters.di.modules.PlayerModule
import com.monke.triviamasters.di.modules.RoomModule
import com.monke.triviamasters.di.modules.UserModule
import dagger.BindsInstance
import dagger.Component


@Component(modules = [
    AppModule::class,
    PlayerModule::class,
    UserModule::class,
    FirebaseModule::class,
    RoomModule::class
])
@AppScope
interface AppComponent {

    fun loginComponent(): LoginComponent.Factory

    fun gameComponent(): GameComponent.Factory


    @Component.Builder
    interface Builder {

        @BindsInstance
        fun context(context: Application): Builder

        fun build() : AppComponent
    }
}