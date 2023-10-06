package com.monke.triviamasters

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.monke.triviamasters.data.remote.TriviaApi
import com.monke.triviamasters.di.components.GameComponent
import com.monke.triviamasters.di.components.LoginComponent
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

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

        val client = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor()).build()

        val base = "https://jservice.io/"
        val retrofit = Retrofit.Builder()
            .baseUrl(base)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val triviaApi = retrofit.create(TriviaApi::class.java)

        lifecycleScope.launch {
            val res = triviaApi.getRandomQuestions(100)
            Log.d("ROMAN EMPIRE", res.code().toString())
            if (res.isSuccessful)
                res.body()?.let {
                    Log.d("ALALALALAL", "questions: ${it.map { it.category.toString() }}")
                    Log.d("ALALALALAL", "questions: ${it.size}")
                }

        }

    }


}