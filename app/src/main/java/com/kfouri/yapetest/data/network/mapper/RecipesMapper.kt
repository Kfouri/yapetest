package com.kfouri.yapetest.data.network.mapper

import com.kfouri.yapetest.data.network.response.RecipesResponse
import com.kfouri.yapetest.domain.model.Origen
import com.kfouri.yapetest.domain.model.Recipe
import com.kfouri.yapetest.domain.model.RecipesModel

class RecipesMapper {

    fun toModel(recipesResponse: RecipesResponse): RecipesModel {

        val modelList = ArrayList<Recipe>()
        recipesResponse.recipes.forEach { item ->
            val model = Recipe(
                id = item.id,
                name = item.name,
                image = item.image,
                description = item.description,
                ingredients = item.ingredients,
                origen = Origen(item.origen?.lat ?: 0.0, item.origen?.lon ?: 0.0)
            )
            modelList.add(model)
        }

        return RecipesModel(modelList)
    }

}