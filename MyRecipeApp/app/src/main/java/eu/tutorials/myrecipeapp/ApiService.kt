package eu.tutorials.myrecipeapp

import eu.tutorials.myrecipeapp.data.CategoriesResponse
import eu.tutorials.myrecipeapp.data.MealByCategory
import eu.tutorials.myrecipeapp.data.dishRacipe
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Query


private val retrofit = Retrofit.Builder().baseUrl("https://www.themealdb.com/api/json/v1/1/")
    .addConverterFactory(GsonConverterFactory.create())
    .build()

val recipeService = retrofit.create(ApiService::class.java)

interface ApiService{
    @GET("categories.php")
    suspend fun getCategories(): CategoriesResponse

    @GET("filter.php")
    suspend fun getMealListByCategory(
        @Query("c") categoryname:String
    ) : MealByCategory

    @GET("search.php")
    suspend fun getMealRacipe(
        @Query("s") mealName:String
    ) : dishRacipe
}