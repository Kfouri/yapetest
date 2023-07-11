package com.kfouri.yapetest.data.client

import com.kfouri.yapetest.data.network.response.RecipeResponse
import com.kfouri.yapetest.data.network.response.RecipesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface RecipeClient {

    @GET("recipes")
    suspend fun getRecipes(): Response<RecipesResponse>

    @GET("recipe/{id}")
    suspend fun getRecipe(@Path("id") id: Int): Response<RecipeResponse>
}