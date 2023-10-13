package com.monke.triviamasters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.monke.triviamasters.di.components.GameComponent
import com.monke.triviamasters.di.components.LoginComponent

class MainActivity : AppCompatActivity() {

    lateinit var mainNavController: NavController

    lateinit var loginComponent: LoginComponent
    lateinit var  gameComponent: GameComponent

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
                as NavHostFragment
        mainNavController = navHostFragment.navController
        loginComponent = (application as App).appComponent.loginComponent().create()
        gameComponent = (application as App).appComponent.gameComponent().create()


    }


}