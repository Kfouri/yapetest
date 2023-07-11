package com.kfouri.yapetest.data

import com.kfouri.yapetest.data.client.RecipeService
import com.kfouri.yapetest.data.model.Resource
import com.kfouri.yapetest.data.network.mapper.RecipeMapper
import com.kfouri.yapetest.data.network.mapper.RecipesMapper
import com.kfouri.yapetest.domain.model.Recipe
import com.kfouri.yapetest.domain.model.RecipesModel
import com.kfouri.yapetest.domain.repository.RecipeRepository
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class RecipeRepositoryImpl @Inject constructor(
    private val service: RecipeService
) : RecipeRepository {

    override suspend fun getRecipes(): Resource<RecipesModel> {

        val response = service.getRecipes()

        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(RecipesMapper().toModel(result))
            }
        }
        return Resource.Error("Error reading service https")
    }

    override suspend fun getRecipe(id: Int): Resource<Recipe> {
        val response = service.getRecipe(id)

        if (response.isSuccessful) {
            response.body()?.let { result ->
                return Resource.Success(RecipeMapper().toModel(result))
            }
        }
        return Resource.Error("Error reading service https")
    }
}