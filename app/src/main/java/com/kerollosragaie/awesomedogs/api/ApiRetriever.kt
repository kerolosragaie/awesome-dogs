package com.kerollosragaie.awesomedogs.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

private const val BASE_URL = "https://random.dog"

class ApiRetriever {
    private val service: ApiRequest = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiRequest::class.java)

    fun getRandDog() = service.getRandomDog()

}