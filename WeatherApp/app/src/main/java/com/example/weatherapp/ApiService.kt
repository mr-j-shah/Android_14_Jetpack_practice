package com.example.weatherapp

import com.example.weatherapp.Data.new_weather
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query

private val retrofitObj = Retrofit.Builder().baseUrl("http://api.weatherstack.com/")
    .addConverterFactory(GsonConverterFactory.create())
    .client(OkHttpClient())
    .build()

val weatherApiService = retrofitObj.create(ApiService::class.java)

interface ApiService {
    @GET("current")
    suspend fun getWeather(
        @Query("access_key") access_key: String,
        @Query("query") query: String
    ): new_weather

}