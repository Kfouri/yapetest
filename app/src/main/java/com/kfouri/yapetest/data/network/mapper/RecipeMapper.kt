package com.kfouri.yapetest.data.network.mapper

import com.kfouri.yapetest.data.network.response.RecipeResponse
import com.kfouri.yapetest.domain.model.Origen
import com.kfouri.yapetest.domain.model.Recipe

class RecipeMapper {

    fun toModel(recipeResponse: RecipeResponse): Recipe {
        return Recipe(
            id = recipeResponse.id,
            name = recipeResponse.name,
            image = recipeResponse.image,
            description = recipeResponse.description,
            ingredients = recipeResponse.ingredients,
            origen = Origen(recipeResponse.origen?.lat ?: 0.0, recipeResponse.origen?.lon ?: 0.0)
        )
    }
}