package com.example.dictionary.Retrofit

import com.example.dictionary.modelData.DictModelItem
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RetrofitService{
    @GET("/api/v2/entries/en/{word}")
    suspend fun getWord(
        @Path("word") word:String
    ): Response<List<DictModelItem>>
}