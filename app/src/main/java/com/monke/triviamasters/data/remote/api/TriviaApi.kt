package com.monke.triviamasters.data.remote.api

import com.monke.triviamasters.data.remote.dto.CategoryRemote
import com.monke.triviamasters.data.remote.dto.QuestionRemote
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface TriviaApi {

    @GET("/api/random")
    suspend fun getRandomQuestions(@Query("count") count: Int): Response<List<QuestionRemote>>

    @GET("/api/clues")
    suspend fun getQuestionsBySettings(
        @Query("value") value: Int? = null,
        @Query("offset") offset: Int? = null,
        @Query("category") categoryId: Int? = null
    ): Response<List<QuestionRemote>>

    @GET("/api/categories")
    suspend fun getCategories(
        @Query("count") count: Int? = null,
        @Query("offset") offset: Int? = null
    ): Response<List<CategoryRemote>>


}