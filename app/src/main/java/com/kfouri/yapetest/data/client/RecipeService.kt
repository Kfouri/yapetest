package com.kfouri.yapetest.data.client

import com.kfouri.yapetest.data.network.response.RecipeResponse
import com.kfouri.yapetest.data.network.response.RecipesResponse
import retrofit2.Response
import javax.inject.Inject

class RecipeService @Inject constructor(
    private val recipeClient: RecipeClient
) {

    suspend fun getRecipes(): Response<RecipesResponse> {
        return recipeClient.getRecipes()
    }

    suspend fun getRecipe(id: Int): Response<RecipeResponse> {
        return recipeClient.getRecipe(id)
    }
}