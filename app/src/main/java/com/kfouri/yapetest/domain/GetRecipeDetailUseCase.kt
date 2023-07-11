package com.kfouri.yapetest.domain

import com.kfouri.yapetest.data.model.Resource
import com.kfouri.yapetest.domain.model.Recipe
import com.kfouri.yapetest.domain.repository.RecipeRepository
import javax.inject.Inject

class GetRecipeDetailUseCase @Inject constructor(
    private val repository: RecipeRepository
) {
    suspend operator fun invoke(id: Int): Resource<Recipe> = repository.getRecipe(id)
}