package com.kfouri.yapetest.domain.repository

import com.kfouri.yapetest.data.model.Resource
import com.kfouri.yapetest.domain.model.Recipe
import com.kfouri.yapetest.domain.model.RecipesModel

interface RecipeRepository {

    suspend fun getRecipes(): Resource<RecipesModel>
    suspend fun getRecipe(id: Int): Resource<Recipe>

}