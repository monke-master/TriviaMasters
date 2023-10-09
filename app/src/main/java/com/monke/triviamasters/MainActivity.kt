package com.monke.triviamasters

import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.lifecycle.lifecycleScope
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.monke.triviamasters.data.remote.TriviaApi
import com.monke.triviamasters.di.components.GameComponent
import com.monke.triviamasters.di.components.LoginComponent
import kotlinx.coroutines.launch
import kotlinx.coroutines.tasks.await
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import kotlin.random.Random

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

        val hashMap = hashMapOf<String, Any>(
            "email" to "hdhdhd"
        )
        // Create a new user with a first and last name
        val user = hashMapOf(
            "first" to "Ada",
            "last" to "Berkof",
            "born" to 1012,
        )

        val db = FirebaseFirestore.getInstance()
        lifecycleScope.launch {
//            db.collection("users")
//                .add(user).await()
            val res = db.collection("users")
                .get().await()
            for (document in res) {
                Log.d("ROMAN EMPIRE", "${document.id} => ${document.data}")
            }

        }
// Add a new document with a generated ID

    }


}