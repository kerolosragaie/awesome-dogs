package com.kerollosragaie.awesomedogs.api

import retrofit2.Call
import retrofit2.http.GET


interface ApiRequest {
    @GET("/woof.json?ref=apilist.fun")
    fun getRandomDog(): Call<DogsData>
}