package com.kfouri.yapetest.data.network.response

data class RecipesResponse(
    val recipes: List<RecipeResponse>
)

data class RecipeResponse(
    val id: Int?,
    val name: String?,
    val image: String?,
    val description: String?,
    val ingredients: String?,
    val origen: OrigenResponse?
)

data class OrigenResponse(
    val lat: Double = 0.0,
    val lon: Double = 0.0
)