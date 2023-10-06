package com.monke.triviamasters.di.modules

import com.monke.triviamasters.data.remote.TriviaApi
import com.monke.triviamasters.data.remote.BASE_URL
import com.monke.triviamasters.di.GameFragmentScope
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
class TriviaApiModule {

    @Provides
    @GameFragmentScope
    fun provideRetrofit(): Retrofit {
        val okHttpClient = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .build()


        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }

    @Provides
    @GameFragmentScope
    fun provideApi(retrofit: Retrofit): TriviaApi = retrofit.create(TriviaApi::class.java)

}