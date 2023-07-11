package com.kfouri.yapetest.domain

import com.kfouri.yapetest.data.model.Resource
import com.kfouri.yapetest.domain.model.RecipesModel
import com.kfouri.yapetest.domain.repository.RecipeRepository
import javax.inject.Inject

class GetRecipesUseCase @Inject constructor(
    private val repository: RecipeRepository
){
    suspend operator fun invoke(): Resource<RecipesModel> = repository.getRecipes()
}