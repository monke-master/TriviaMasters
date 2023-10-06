package com.monke.triviamasters.data.remote

import com.monke.triviamasters.data.remote.dto.QuestionRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaApi {

    @GET("/api/random")
    suspend fun getRandomQuestions(@Query("count") count: Int): Response<List<QuestionRemote>>


}