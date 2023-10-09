package com.monke.triviamasters.di.modules

import android.app.Application
import android.content.Context
import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.google.firebase.ktx.initialize
import com.monke.triviamasters.App
import com.monke.triviamasters.di.LoginFragmentScope
import dagger.Module
import dagger.Provides

@Module
class FirebaseModule {

    @Provides
    fun provideFirebaseAuth(context: Application): FirebaseAuth {
        //FirebaseApp.initializeApp(context)
        return FirebaseAuth.getInstance()
    }


}